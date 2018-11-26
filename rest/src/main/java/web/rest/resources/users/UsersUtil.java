package web.rest.resources.users;

import static web.rest.tools.conversion.ConversionUtil.toDataObject;
import static web.rest.tools.conversion.ConversionUtil.toEventDataObjectList;
import static web.rest.tools.conversion.ConversionUtil.toLocationDataObjectList;
import static web.rest.tools.conversion.ConversionUtil.toUserDataObjectList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import hibernate.search.UserSearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.events.model.EventData;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.users.model.UserData;
import web.rest.resources.users.model.UserDetailsData;

@Service
public class UsersUtil {

	@Autowired
	private UserDao userDao;

	public List<UserData> getUsers() {
		return toUserDataObjectList(this.userDao.getUsers());
	}

	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
		return toUserDataObjectList(this.userDao.getUsers(searchParams, sortParams));
	}

	public UserData getUserByLogin(String login) {
		User user = this.userDao.findByLogin(login);
		if (user != null) {
			return toDataObject(user);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public void updateUserDetails(String login, UserDetailsData userDetails) {
		User user = this.userDao.findByLogin(login);

		if (user != null) {
			user.setName(userDetails.getName());
			user.setSurname(userDetails.getSurname());
			user.setLocation(userDetails.getLocation());
			user.setDateOfBirth(userDetails.getDateOfBirth());
			this.userDao.updateUser(user);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public List<LocationData> getUserLocations(String login) {

		User user = this.userDao.findByLogin(login);
		if (user == null) {
			throw new ResourceNotFoundException();
		}

		return toLocationDataObjectList(user.getLocations());
	}

	public List<EventData> getUserEvents(String login) {

		User user = this.userDao.findByLogin(login);
		if (user == null) {
			throw new ResourceNotFoundException();
		}

		return toEventDataObjectList(user.getEvents());
	}
}
