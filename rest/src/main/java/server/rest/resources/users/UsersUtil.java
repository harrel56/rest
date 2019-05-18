package server.rest.resources.users;

import hibernate.dao.UserDao;
import hibernate.entities.User;
import hibernate.search.SearchParams;
import hibernate.search.UserSearchParams;
import hibernate.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import server.rest.resources.attendances.model.AttendanceData;
import server.rest.resources.events.model.EventData;
import server.rest.resources.locations.model.LocationData;
import server.rest.resources.pagination.PaginationParams;
import server.rest.resources.users.model.UserData;
import server.rest.resources.users.model.UserDetailsData;

import java.util.List;

import static server.rest.tools.conversion.ConversionUtil.*;

@Service
public class UsersUtil {

    @Autowired
    private UserDao userDao;

    public List<UserData> getUsers() {
        return toUserDataObjectList(this.userDao.getUsers());
    }

    public Long getUsersCount(SearchParams<User> searchParams) {
        return this.userDao.getUsersCount(searchParams);
    }

    public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams, PaginationParams paginationParams) {
        return toUserDataObjectList(this.userDao.getUsers(searchParams, sortParams, paginationParams));
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

    public List<AttendanceData> getUserAttendances(String login) {

        User user = this.userDao.findByLogin(login);
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        return toAttendanceDataObjectList(user.getAttendances());
    }
}
