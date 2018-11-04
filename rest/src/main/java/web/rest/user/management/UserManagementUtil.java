package web.rest.user.management;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import web.rest.email.EmailUtils;
import web.rest.user.management.activation.UserActivationResponseData;
import web.rest.user.management.register.UserRegistrationRequestData;
import web.rest.user.management.register.UserRegistrationResponseData;

@Service
public class UserManagementUtil {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailUtils emailUtils;

	@Value("${user-management.activation-string-length}")
	private int activationStringLength;

	public UserRegistrationResponseData validateRegistrationData(UserRegistrationRequestData registrationData,
			Locale locale) {

		if (!this.isLoginUnique(registrationData.getLogin())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.LOGIN_ALREADY_TAKEN,
					this.messageSource.getMessage("userManagement.loginTaken", null, null, locale));
		}
		if (!this.isEmailUnique(registrationData.getEmail())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.EMAIL_ALREADY_TAKEN,
					this.messageSource.getMessage("userManagement.emailTaken", null, null, locale));
		}
		if (!this.isLoginValid(registrationData.getLogin())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.LOGIN_INVALID,
					this.messageSource.getMessage("userManagement.loginInvalid", null, null, locale));
		}
		if (!this.isEmailValid(registrationData.getEmail())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.EMAIL_INVALID,
					this.messageSource.getMessage("userManagement.emailInvalid", null, null, locale));
		}
		if (!this.isPasswordValid(registrationData.getPassword())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.PASSWORD_INVALID,
					this.messageSource.getMessage("userManagement.passwordInvalid", null, null, locale));
		}

		return null;
	}

	public UserRegistrationResponseData createSuccessfulRegistrationResponse(Locale locale) {
		return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.CREATED,
				this.messageSource.getMessage("userManagement.userCreated", null, null, locale));
	}

	public UserRegistrationResponseData createFailedRegistrationResponse(Locale locale) {
		return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.UNKNOWN,
				this.messageSource.getMessage("userManagement.unknownError", null, null, locale));
	}

	public UserActivationResponseData createSuccessfulActivationResponse(Locale locale) {
		return new UserActivationResponseData(UserActivationResponseData.ResponseState.ACTIVATED,
				this.messageSource.getMessage("userManagement.activationSuccess", null, null, locale));
	}

	public UserActivationResponseData createFailedActivationResponse(Locale locale) {
		return new UserActivationResponseData(UserActivationResponseData.ResponseState.FAILED,
				this.messageSource.getMessage("userManagement.activationFail", null, null, locale));
	}

	/**
	 * Encode password, generate activation string, put all in DB and send
	 * activation email
	 * 
	 * @param userData
	 * @throws MessagingException
	 */
	public void addNewUser(UserRegistrationRequestData userData, Locale locale) throws MessagingException {
		User user = new User(null, userData.getLogin(), userData.getEmail(),
				this.encoder.encode(userData.getPassword()));
		user.setActivationString(this.generateActivationString());
		this.userDao.addUser(user);

		this.emailUtils.sendActivationEmailAsync(user.getEmail(), user.getLogin(), user.getActivationString(), locale);
	}

	public boolean performUserActivation(String login, String activationString) {
		User user = this.getSingleUserByLogin(login);
		if (activationString.equals(user.getActivationString())) {
			user.setActivationString(null);
			this.userDao.updateUser(user);
			return true;
		} else {
			return false;
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
