package web.rest.resources.locations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import hibernate.dao.LocationDao;
import hibernate.dao.UserDao;
import hibernate.entities.Location;
import hibernate.entities.User;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.locations.model.LocationDetailsData;
import web.rest.resources.users.UsersUtil;

@Service
public class LocationsUtil {

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UsersUtil usersUtil;

	public List<LocationData> getLocations() {
		return this.toDataObjectList(this.locationDao.getLocations());
	}

//	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
//		return this.toDataObjectList(this.locationDao.getLocations());
//	}

	public LocationData createLocation(LocationDetailsData locationDetails, String creatorLogin) {

		User creator = this.userDao.findByLogin(creatorLogin);

		if (creator != null) {

			Location location = new Location();
			location.setCreator(creator);
			location.setName(locationDetails.getName());
			location.setDescription(locationDetails.getDescription());
			location.setLatitude(locationDetails.getLatitude());
			location.setLongitude(locationDetails.getLongitude());
			location.setState(locationDetails.getState().name());

			this.locationDao.addLocation(location);
			return this.toDataObject(location);
		} else {
			throw new AccessDeniedException("Location creator not found!");
		}
	}

	public List<LocationData> toDataObjectList(List<Location> locations) {
		List<LocationData> locationDatas = new ArrayList<>(locations.size());
		for (Location location : locations) {
			locationDatas.add(this.toDataObject(location));
		}
		return locationDatas;
	}

	public LocationData toDataObject(Location location) {
		return new LocationData(location.getId(), this.usersUtil.toDataObject(location.getCreator()), new LocationDetailsData(location.getName(),
				location.getDescription(), location.getLatitude(), location.getLongitude(), LocationDetailsData.State.valueOf(location.getState())),
				location.getCreateTime(), location.getModifyTime());
	}
}
