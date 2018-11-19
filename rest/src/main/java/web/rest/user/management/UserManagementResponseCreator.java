package web.rest.user.management;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import web.rest.user.management.activation.UserActivationResponseData;
import web.rest.user.management.passwordchange.PasswordChangeResponseData;
import web.rest.user.management.register.UserRegistrationResponseData;

@Service
public class UserManagementResponseCreator {

	@Autowired
	private MessageSource messageSource;

	public ResponseEntity<UserRegistrationResponseData> createRegistrationResponse(Locale locale,
			UserRegistrationResponseData.ResponseState state) {

		switch (state) {
		case CREATED:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.userCreated", null, locale)),
					HttpStatus.CREATED);
		case LOGIN_ALREADY_TAKEN:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.loginTaken", null, locale)),
					HttpStatus.BAD_REQUEST);
		case EMAIL_ALREADY_TAKEN:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.emailTaken", null, locale)),
					HttpStatus.BAD_REQUEST);
		case LOGIN_INVALID:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.loginInvalid", null, locale)),
					HttpStatus.BAD_REQUEST);
		case EMAIL_INVALID:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.emailInvalid", null, locale)),
					HttpStatus.BAD_REQUEST);
		case PASSWORD_INVALID:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.passwordInvalid", null, locale)),
					HttpStatus.BAD_REQUEST);
		default:
			state = UserRegistrationResponseData.ResponseState.UNKNOWN_ERROR;
		case UNKNOWN_ERROR:
			return new ResponseEntity<>(
					new UserRegistrationResponseData(state,
							this.messageSource.getMessage("userManagement.unknownError", null, locale)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<UserActivationResponseData> createActivationResponse(Locale locale,
			UserActivationResponseData.ResponseState state) {

		switch (state) {
		case ACTIVATED:
			return new ResponseEntity<>(
					new UserActivationResponseData(state,
							this.messageSource.getMessage("userManagement.activationSuccess", null, locale)),
					HttpStatus.BAD_REQUEST);
		case FAILED:
			return new ResponseEntity<>(
					new UserActivationResponseData(state,
							this.messageSource.getMessage("userManagement.activationFailed", null, locale)),
					HttpStatus.OK);
		default:
			state = UserActivationResponseData.ResponseState.UNKNOWN_ERROR;
		case UNKNOWN_ERROR:
			return new ResponseEntity<>(
					new UserActivationResponseData(state,
							this.messageSource.getMessage("userManagement.unknownError", null, locale)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<PasswordChangeResponseData> createPasswordChangeResponse(Locale locale,
			PasswordChangeResponseData.ResponseState state) {

		switch (state) {
		case CHANGED:
			return new ResponseEntity<>(
					new PasswordChangeResponseData(state,
							this.messageSource.getMessage("userManagement.passwordChanged", null, locale)),
					HttpStatus.OK);
		case PASSWORD_THE_SAME:
			return new ResponseEntity<>(
					new PasswordChangeResponseData(state,
							this.messageSource.getMessage("userManagement.passwordTheSame", null, locale)),
					HttpStatus.BAD_REQUEST);
		case PASSWORD_INVALID:
			return new ResponseEntity<>(
					new PasswordChangeResponseData(state,
							this.messageSource.getMessage("userManagement.oldPasswordInvalid", null, locale)),
					HttpStatus.BAD_REQUEST);
		case NEW_PASSWORD_INVALID:
			return new ResponseEntity<>(
					new PasswordChangeResponseData(state,
							this.messageSource.getMessage("userManagement.newPasswordInvalid", null, locale)),
					HttpStatus.BAD_REQUEST);
		default:
			state = PasswordChangeResponseData.ResponseState.UNKNOWN_ERROR;
		case UNKNOWN_ERROR:
			return new ResponseEntity<>(
					new PasswordChangeResponseData(state,
							this.messageSource.getMessage("userManagement.unknownError", null, locale)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
