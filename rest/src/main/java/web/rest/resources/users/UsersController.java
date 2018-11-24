package web.rest.resources.users;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hibernate.entities.User;
import hibernate.search.UserSearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.users.model.UserData;
import web.rest.resources.users.model.UserDetailsData;

@RestController
@RequestMapping("/users")
public class UsersController {

	@SuppressWarnings("unused")
	private static transient final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UsersUtil usersUtil;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public List<UserData> getUsers(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestParam(name = "login", required = false) String login, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "surname", required = false) String surname, @RequestParam(name = "location", required = false) String location,
			@RequestParam(name = "sort", required = false) String[] sorts) {

		return this.usersUtil.getUsers(new UserSearchParams(login, name, surname, location), new SortParams<User>(User.class, sorts));
	}

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public UserData getUser(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable String login) {

		return this.usersUtil.getUserByLogin(login);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/{login}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable String login,
			@RequestBody UserDetailsData userDetails) {

		if (login.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			this.usersUtil.updateUserDetails(login, userDetails);
		} else {
			throw new AccessDeniedException("Invalid token for request operation!");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{login}/locations", method = RequestMethod.GET)
	public List<LocationData> getUserLocations(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@PathVariable String login) {

		return this.usersUtil.getUserLocations(login);
	}
}
