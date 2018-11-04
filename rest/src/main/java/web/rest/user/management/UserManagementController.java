package web.rest.user.management;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.rest.user.management.activation.UserActivationResponseData;
import web.rest.user.management.register.UserRegistrationRequestData;
import web.rest.user.management.register.UserRegistrationResponseData;

@RestController
@RequestMapping("/user-management")
public class UserManagementController {

	private static transient final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@Autowired
	private UserManagementUtil userManagementUtil;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponseData> register(
			@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestBody UserRegistrationRequestData userData) {

		UserRegistrationResponseData responseData = null;
		HttpStatus httpStatus = null;

		UserRegistrationResponseData badRequestResponse = this.userManagementUtil.validateRegistrationData(userData,
				locale);
		if (badRequestResponse != null) {
			responseData = badRequestResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {

			try {
				this.userManagementUtil.addNewUser(userData, locale);
				responseData = this.userManagementUtil.createSuccessfulRegistrationResponse(locale);
				httpStatus = HttpStatus.CREATED;
			} catch (Exception e) {
				logger.error(e.getMessage());
				responseData = this.userManagementUtil.createFailedRegistrationResponse(locale);
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}

		return new ResponseEntity<UserRegistrationResponseData>(responseData, httpStatus);
	}

	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseEntity<UserActivationResponseData> activate(
			@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @RequestParam String login,
			@RequestParam("a_str") String activationString) {

		try {
			if (this.userManagementUtil.performUserActivation(login, activationString)) {
				return new ResponseEntity<UserActivationResponseData>(
						this.userManagementUtil.createSuccessfulActivationResponse(locale), HttpStatus.OK);
			} else {
				return new ResponseEntity<UserActivationResponseData>(
						this.userManagementUtil.createFailedActivationResponse(locale), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<UserActivationResponseData>(
					this.userManagementUtil.createFailedActivationResponse(locale), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/registerAuth", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponseData> registerAuth(
			@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestBody UserRegistrationRequestData userData) {

		UserRegistrationResponseData responseData = null;
		HttpStatus httpStatus = null;

		UserRegistrationResponseData badRequestResponse = this.userManagementUtil.validateRegistrationData(userData,
				locale);
		if (badRequestResponse != null) {
			responseData = badRequestResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {

			try {
				this.userManagementUtil.addNewUser(userData, locale);
				responseData = this.userManagementUtil.createSuccessfulRegistrationResponse(locale);
				httpStatus = HttpStatus.CREATED;
			} catch (Exception e) {
				logger.error(e.getMessage());
				responseData = this.userManagementUtil.createFailedRegistrationResponse(locale);
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}

		return new ResponseEntity<UserRegistrationResponseData>(responseData, httpStatus);
	}
}
