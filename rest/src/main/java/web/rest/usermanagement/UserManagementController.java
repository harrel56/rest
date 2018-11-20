package web.rest.usermanagement;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.rest.usermanagement.activation.ResendActivationResponseData;
import web.rest.usermanagement.activation.UserActivationResponseData;
import web.rest.usermanagement.passwordchange.PasswordChangeRequestData;
import web.rest.usermanagement.passwordchange.PasswordChangeResponseData;
import web.rest.usermanagement.register.UserRegistrationRequestData;
import web.rest.usermanagement.register.UserRegistrationResponseData;

@RestController
@RequestMapping("/user-management")
public class UserManagementController {

	@SuppressWarnings("unused")
	private static transient final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@Autowired
	private UserManagementUtil userManagementUtil;

	@Autowired
	private UserManagementResponseCreator responseCreator;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponseData> register(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@Valid @RequestBody UserRegistrationRequestData userData) {

		UserRegistrationResponseData.ResponseState state = this.userManagementUtil.registerNewUser(userData);

		if (state == UserRegistrationResponseData.ResponseState.CREATED) {
			this.userManagementUtil.sendActivationEmail(locale, userData.getLogin());
		}

		return this.responseCreator.createRegistrationResponse(locale, state);
	}

	@RequestMapping(value = "/resend-activation", method = RequestMethod.GET)
	public ResponseEntity<ResendActivationResponseData> resendActivation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestParam String login) {

		ResendActivationResponseData.ResponseState state = this.userManagementUtil.resendActivation(locale, login);

		return this.responseCreator.createResendActivationResponse(locale, state);
	}

	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseEntity<UserActivationResponseData> activate(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestParam String login, @RequestParam("a_str") String activationString) {

		UserActivationResponseData.ResponseState state = this.userManagementUtil.performUserActivation(login, activationString);

		return this.responseCreator.createActivationResponse(locale, state);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<PasswordChangeResponseData> changePassword(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@Valid @RequestBody PasswordChangeRequestData passwordData) {

		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		PasswordChangeResponseData.ResponseState state = this.userManagementUtil.changeUserPassword(login, passwordData.getOldPassword(),
				passwordData.getNewPassword());

		return this.responseCreator.createPasswordChangeResponse(locale, state);
	}
}
