package web.rest.user.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hibernate.entities.User;

@RestController
@RequestMapping("/user-management")
public class UserManagementController {

	private static transient final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@Autowired
	private UserManagementUtil userManagementUtil;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponseData> greeting(@RequestBody UserRegistrationRequestData userData) {

		UserRegistrationResponseData responseData = null;
		HttpStatus httpStatus = null;

		UserRegistrationResponseData.ResponseState state = this.userManagementUtil.validateRegistrationData(userData);
		if (state != null) {
			responseData = new UserRegistrationResponseData(state, "Login already exists!");
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {

			try {
				this.userManagementUtil.addNewUser(User.createFromRegistrationData(userData));
				responseData = new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.CREATED,
						"User created succesfully!");
				httpStatus = HttpStatus.CREATED;
			} catch (Exception e) {
				logger.error(e.getMessage());
				responseData = new UserRegistrationResponseData(UserRegistrationResponseData.ResponseState.UNKNOWN,
						"Unexpected error occured!");
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}

		return new ResponseEntity<UserRegistrationResponseData>(responseData, httpStatus);
	}
}
