package web.rest.resources.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import hibernate.search.UserSearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.users.model.UserData;
import web.rest.resources.users.model.UserDetailsData;

@Service
public class UsersUtil {

	@Autowired
	private UserDao userDao;

	public List<UserData> getUsers() {
		return this.toDataObjectList(this.userDao.getUsers());
	}

	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
		return this.toDataObjectList(this.userDao.getUsers(searchParams, sortParams));
	}

	public UserData getUserByLogin(String login) {
		User user = this.userDao.findByLogin(login);
		if (user != null) {
			return this.toDataObject(user);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public void updateUserDetails(String login, UserDetailsData userDetails) {
		User user = this.userDao.findByLogin(login);

		if (user != null) {
			if (userDetails.getName() != null) {
				user.setName(userDetails.getName());
			}
			if (userDetails.getSurname() != null) {
				user.setSurname(userDetails.getSurname());
			}
			if (userDetails.getLocation() != null) {
				user.setLocation(userDetails.getLocation());
			}
			if (userDetails.getDateOfBirth() != null) {
				user.setDateOfBirth(userDetails.getDateOfBirth());
			}

			this.userDao.updateUser(user);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public List<UserData> toDataObjectList(List<User> users) {
		List<UserData> userDatas = new ArrayList<>(users.size());
		for (User user : users) {
			userDatas.add(this.toDataObject(user));
		}
		return userDatas;
	}

	public UserData toDataObject(User user) {
		return new UserData(user.getId(), user.getLogin(), user.getCreateTime(), user.getModifyTime(),
				new UserDetailsData(user.getName(), user.getSurname(), user.getLocation(), user.getDateOfBirth()));
	}
}
