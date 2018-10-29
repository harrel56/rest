package web.rest.user.management;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;

@Service
public class UserManagementUtil {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BCryptPasswordEncoder encoder;

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

	public void addNewUser(User user) {
		this.userDao.addUser(user);
	}

	public void addNewUser(UserRegistrationRequestData userData) {
		User user = new User(null, userData.getLogin(), userData.getEmail(),
				this.encoder.encode(userData.getPassword()), null);
		this.userDao.addUser(user);
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
}
