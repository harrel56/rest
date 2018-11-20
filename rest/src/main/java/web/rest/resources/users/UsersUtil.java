package web.rest.resources.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import web.rest.resources.users.model.UserData;

@Service
public class UsersUtil {

	@Autowired
	private UserDao userDao;

	public List<UserData> getUsers() {
		return this.toDataObjectList(this.userDao.getUsers());
	}

	public UserData getUserByLogin(String login) {
		List<User> users = this.userDao.findByLogin(login);
		if (!users.isEmpty()) {
			return this.toDataObject(users.get(0));
		} else {
			throw new ResourceNotFoundException();
		}
	}

	private List<UserData> toDataObjectList(List<User> users) {
		List<UserData> userDatas = new ArrayList<>(users.size());
		for (User user : users) {
			userDatas.add(this.toDataObject(user));
		}
		return userDatas;
	}

	private UserData toDataObject(User user) {
		return new UserData(user.getId(), user.getLogin(), user.getCreateTime(), user.getModifyTime());
	}
}
