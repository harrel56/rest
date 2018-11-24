package web.rest.usermanagement;

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
import web.rest.tools.email.EmailUtil;
import web.rest.usermanagement.activation.ResendActivationResponseData;
import web.rest.usermanagement.activation.UserActivationResponseData;
import web.rest.usermanagement.passwordchange.PasswordChangeResponseData;
import web.rest.usermanagement.register.UserRegistrationRequestData;
import web.rest.usermanagement.register.UserRegistrationResponseData;

@Service
public class UserManagementUtil {

	private static transient final Logger logger = LoggerFactory.getLogger(UserManagementUtil.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailUtil emailUtil;

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

			User user = new User(null, registrationData.getLogin(), registrationData.getEmail(), this.encoder.encode(registrationData.getPassword()));
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
			User user = this.userDao.findByLogin(login);
			this.emailUtil.sendActivationEmailAsync(user.getEmail(), user.getLogin(), user.getActivationString(), locale);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ResendActivationResponseData.ResponseState resendActivation(Locale locale, String login) {

		try {
			User user = this.userDao.findByLogin(login);
			if (user != null) {
				if (user.getActivationString() != null) {

					user.setActivationString(this.generateActivationString());
					this.emailUtil.sendActivationEmailAsync(user.getEmail(), user.getLogin(), user.getActivationString(), locale);
					this.userDao.updateUser(user);
					return ResendActivationResponseData.ResponseState.RESENT;
				} else {
					return ResendActivationResponseData.ResponseState.USER_ALREADY_ACTIVATED;
				}

			} else {
				return ResendActivationResponseData.ResponseState.USER_INVALID;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResendActivationResponseData.ResponseState.UNKNOWN_ERROR;
		}
	}

	public UserActivationResponseData.ResponseState performUserActivation(String login, String activationString) {

		try {
			User user = this.userDao.findByLogin(login);
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

	public PasswordChangeResponseData.ResponseState changeUserPassword(String login, String oldPassword, String newPassword) {

		if (oldPassword.equals(newPassword)) {
			return PasswordChangeResponseData.ResponseState.PASSWORD_THE_SAME;
		}

		try {
			User user = this.userDao.findByLogin(login);

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

	private boolean isLoginUnique(String login) {
		return this.userDao.findByLogin(login) == null;
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
