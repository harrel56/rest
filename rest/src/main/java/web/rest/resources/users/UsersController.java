package web.rest.resources.users;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.rest.resources.users.model.UserData;

@RestController
@RequestMapping("/users")
public class UsersController {

	@SuppressWarnings("unused")
	private static transient final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UsersUtil usersUtil;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public List<UserData> getUsers(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale) {

		return this.usersUtil.getUsers();
	}

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public ResponseEntity<UserData> getUser(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@PathVariable String login) {

		return new ResponseEntity<>(this.usersUtil.getUserByLogin(login), HttpStatus.OK);
	}
}
