package web.rest.resources.locations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.dao.LocationDao;
import hibernate.entities.Location;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.users.UsersUtil;

@Service
public class LocationsUtil {

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private UsersUtil usersUtil;

	public List<LocationData> getLocations() {
		return this.toDataObjectList(this.locationDao.getLocations());
	}

//	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
//		return this.toDataObjectList(this.locationDao.getLocations());
//	}

	private List<LocationData> toDataObjectList(List<Location> locations) {
		List<LocationData> locationDatas = new ArrayList<>(locations.size());
		for (Location location : locations) {
			locationDatas.add(this.toDataObject(location));
		}
		return locationDatas;
	}

	private LocationData toDataObject(Location location) {
		return new LocationData(location.getId(), this.usersUtil.toDataObject(location.getCreator()), location.getName(), location.getDescription(),
				location.getLatitude(), location.getLongitude(), location.getCreateTime(), location.getModifyTime(),
				LocationData.State.valueOf(location.getState()));
	}
}
