package web.rest.resources.locations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import hibernate.dao.LocationDao;
import hibernate.dao.UserDao;
import hibernate.entities.Location;
import hibernate.entities.User;
import web.rest.resources.ImmutableDataModificationException;
import web.rest.resources.events.EventsUtil;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
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

	@Autowired
	private EventsUtil eventsUtil;

	public List<LocationData> getLocations() {
		return this.toDataObjectList(this.locationDao.getLocations());
	}

//	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
//		return this.toDataObjectList(this.locationDao.getLocations());
//	}

	public LocationData getLocation(Long id) {
		return this.toDataObject(this.locationDao.findLocationById(id));
	}

	public List<EventData> getLocationEvents(Long id) {

		Location location = this.locationDao.findLocationById(id);
		if (location == null) {
			throw new ResourceNotFoundException();
		}

		return this.eventsUtil.toDataObjectList(location.getEvents());
	}

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

	public void updateLocation(Long id, LocationDetailsData locationDetails, String modifierLogin) {

		Location location = this.locationDao.findLocationById(id);
		if (location == null) {
			throw new ResourceNotFoundException();
		}

		User modifier = this.userDao.findByLogin(modifierLogin);
		if (modifier == null || !location.getCreator().getId().equals(modifier.getId())) {
			throw new AccessDeniedException("Only location creator can modify it!");
		}

		if (!location.getLatitude().equals(locationDetails.getLatitude()) || !location.getLongitude().equals(locationDetails.getLongitude())) {
			throw new ImmutableDataModificationException("Latitude and longitude cannot be modified!");
		}

		location.setName(locationDetails.getName());
		location.setDescription(locationDetails.getDescription());
		location.setState(locationDetails.getState().name());

		this.locationDao.updateLocation(location);
	}

	public EventData createLocationEvent(Long id, EventDetailsData eventDetails, String creatorLogin) {

		return this.eventsUtil.createEvent(this.locationDao.findLocationById(id), eventDetails, creatorLogin);
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
