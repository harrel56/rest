package web.rest.user.management;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import web.rest.email.EmailUtils;
import web.rest.user.management.activation.UserActivationResponseData;
import web.rest.user.management.passwordchange.PasswordChangeResponseData;
import web.rest.user.management.register.UserRegistrationRequestData;
import web.rest.user.management.register.UserRegistrationResponseData;

@Service
public class UserManagementUtil {

	private static transient final Logger logger = LoggerFactory.getLogger(UserManagementUtil.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailUtils emailUtils;

	@Value("${user-management.activation-string-length}")
	private int activationStringLength;

	public UserRegistrationResponseData.ResponseState registerNewUser(UserRegistrationRequestData registrationData) {

		try {
			if (!this.isLoginUnique(registrationData.getLogin())) {
				return UserRegistrationResponseData.ResponseState.LOGIN_ALREADY_TAKEN;
			}
			if (!this.isEmailUnique(registrationData.getEmail())) {
				return UserRegistrationResponseData.ResponseState.EMAIL_ALREADY_TAKEN;
			}
			if (!this.isLoginValid(registrationData.getLogin())) {
				return UserRegistrationResponseData.ResponseState.LOGIN_INVALID;
			}
			if (!this.isEmailValid(registrationData.getEmail())) {
				return UserRegistrationResponseData.ResponseState.EMAIL_INVALID;
			}
			if (!this.isPasswordValid(registrationData.getPassword())) {
				return UserRegistrationResponseData.ResponseState.PASSWORD_INVALID;
			}

			User user = new User(null, registrationData.getLogin(), registrationData.getEmail(),
					this.encoder.encode(registrationData.getPassword()));
			user.setActivationString(this.generateActivationString());
			this.userDao.addUser(user);

			return UserRegistrationResponseData.ResponseState.CREATED;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return UserRegistrationResponseData.ResponseState.UNKNOWN_ERROR;
		}

	}

	public void sendActivationEmail(Locale locale, String login) {

		try {
			User user = this.getSingleUserByLogin(login);
			this.emailUtils.sendActivationEmailAsync(user.getEmail(), user.getLogin(), user.getActivationString(),
					locale);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public UserActivationResponseData.ResponseState performUserActivation(String login, String activationString) {

		try {
			User user = this.getSingleUserByLogin(login);
			if (activationString.equals(user.getActivationString())) {
				user.setActivationString(null);
				this.userDao.updateUser(user);
				return UserActivationResponseData.ResponseState.ACTIVATED;
			} else {
				return UserActivationResponseData.ResponseState.FAILED;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return UserActivationResponseData.ResponseState.UNKNOWN_ERROR;
		}
	}

	public PasswordChangeResponseData.ResponseState changeUserPassword(String login, String oldPassword,
			String newPassword) {

		try {
			User user = this.getSingleUserByLogin(login);

			if (!this.encoder.matches(oldPassword, user.getPassword())) {
				return PasswordChangeResponseData.ResponseState.PASSWORD_INVALID;
			} else if (!this.isPasswordValid(newPassword)) {
				return PasswordChangeResponseData.ResponseState.NEW_PASSWORD_INVALID;
			} else {
				user.setPassword(this.encoder.encode(newPassword));
				this.userDao.updateUser(user);
				return PasswordChangeResponseData.ResponseState.CHANGED;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return PasswordChangeResponseData.ResponseState.UNKNOWN_ERROR;
		}
	}

	private User getSingleUserByLogin(String login) {
		List<User> userList = this.userDao.findByLogin(login);
		if (userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}
	}

	private boolean isLoginUnique(String login) {
		return this.userDao.findByLogin(login).isEmpty();
	}

	private boolean isEmailUnique(String email) {
		return this.userDao.findByEmail(email).isEmpty();
	}

	private boolean isLoginValid(String login) {
		String regex = "^[a-zA-Z0-9]{5,20}$";
		return login.matches(regex);
	}

	private boolean isEmailValid(String email) {
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		return email.matches(regex);
	}

	private boolean isPasswordValid(String password) {
		String regex = "^(?=.*[a-zA-Z])(?=\\S+$).{8,}$";
		return password.matches(regex);
	}

	private String generateActivationString() {
		return RandomStringUtils.randomAlphanumeric(this.activationStringLength);
	}
}
