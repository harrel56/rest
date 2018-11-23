package web.rest.resources.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.dao.EventDao;
import hibernate.dao.UserDao;
import hibernate.entities.Event;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.locations.LocationsUtil;
import web.rest.resources.users.UsersUtil;

@Service
public class EventsUtil {

	@Autowired
	private EventDao eventsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private LocationsUtil locationsUtil;

	@Autowired
	private UsersUtil usersUtil;

	public List<EventData> getEvents() {
		return this.toDataObjectList(this.eventsDao.getEvents());
	}

//	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
//		return this.toDataObjectList(this.locationDao.getLocations());
//	}

//	public EventData createLocation(EventDetailsData eventDetails, String creatorLogin) {
//
//		User creator = this.userDao.findByLogin(creatorLogin);
//
//		if (creator != null) {
//
//			Location location = new Location();
//			location.setCreator(creator);
//			location.setName(locationDetails.getName());
//			location.setDescription(locationDetails.getDescription());
//			location.setLatitude(locationDetails.getLatitude());
//			location.setLongitude(locationDetails.getLongitude());
//			location.setState(locationDetails.getState().name());
//
//			this.locationDao.addLocation(location);
//			return this.toDataObject(location);
//		} else {
//			throw new AccessDeniedException("Event creator not found!");
//		}
//	}

	public List<EventData> toDataObjectList(List<Event> events) {
		List<EventData> locationDatas = new ArrayList<>(events.size());
		for (Event event : events) {
			locationDatas.add(this.toDataObject(event));
		}
		return locationDatas;
	}

	public EventData toDataObject(Event event) {
		return new EventData(event.getId(), this.locationsUtil.toDataObject(event.getLocation()), this.usersUtil.toDataObject(event.getCreator()),
				new EventDetailsData(event.getName(), event.getDescription(), event.getStartTime(), event.getEndTime(),
						EventDetailsData.State.valueOf(event.getState())),
				event.getCreateTime(), event.getModifyTime());
	}
}
