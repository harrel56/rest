package web.rest.user.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;

@Service
public class UserManagementUtil {

	@Autowired
	private UserDao userDao;

	public UserRegistrationResponseData validateRegistrationData(UserRegistrationRequestData registrationData) {

		if (!this.isLoginUnique(registrationData.getLogin())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.LOGIN_ALREADY_TAKEN,
					"Login already taken");
		}
		if (!this.isEmailUnique(registrationData.getEmail())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.EMAIL_ALREADY_TAKEN,
					"Email already taken");
		}
		if (!this.isLoginValid(registrationData.getLogin())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.INVALID_LOGIN,
					"Login must contain at least 5 alphanumerical characters");
		}
		if (!this.isEmailValid(registrationData.getEmail())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.INVALID_EMAIL,
					"Invalid email format");
		}
		if (!this.isPasswordValid(registrationData.getPassword())) {
			return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.INVALID_PASSWORD,
					"Password must contain at least 8 characters: at least 1 letter and no whitespaces");
		}

		return null;
	}

	public UserRegistrationResponseData createSuccessfulRegistrationResponse() {
		return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.CREATED,
				"User created successfully");
	}

	public UserRegistrationResponseData createFailedRegistrationResponse() {
		return new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.UNKNOWN,
				"Unexpected error occured");
	}

	public void addNewUser(User user) {
		this.userDao.addUser(user);
	}

	private boolean isLoginUnique(String login) {
		return this.userDao.findByLogin(login).isEmpty();
	}

	private boolean isEmailUnique(String email) {
		return this.userDao.findByLogin(email).isEmpty();
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
