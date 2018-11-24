package web.rest.resources.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import hibernate.dao.EventDao;
import hibernate.dao.UserDao;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.locations.LocationsUtil;
import web.rest.resources.users.UsersUtil;

@Service
public class EventsUtil {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private LocationsUtil locationsUtil;

	@Autowired
	private UsersUtil usersUtil;

	public List<EventData> getEvents() {
		return this.toDataObjectList(this.eventDao.getEvents());
	}

//	public List<UserData> getUsers(UserSearchParams searchParams, SortParams<User> sortParams) {
//		return this.toDataObjectList(this.locationDao.getLocations());
//	}

	public EventData getEvent(Long id) {
		return this.toDataObject(this.eventDao.findEventById(id));
	}

	public EventData createEvent(Location location, EventDetailsData eventDetails, String creatorLogin) {

		User creator = this.userDao.findByLogin(creatorLogin);
		if (creator == null) {
			throw new AccessDeniedException("Event creator not found!");
		}
		if (location == null) {
			throw new ResourceNotFoundException();
		}

		Event event = new Event();
		event.setLocation(location);
		event.setCreator(creator);
		event.setName(eventDetails.getName());
		event.setDescription(eventDetails.getDescription());
		event.setStartTime(eventDetails.getStartTime());
		event.setEndTime(eventDetails.getEndTime());
		event.setState(eventDetails.getState().name());

		this.eventDao.addEvent(event);
		return this.toDataObject(event);
	}

	public void updateEvent(Long id, EventDetailsData eventDetails, String modifierLogin) {

		Event event = this.eventDao.findEventById(id);
		if (event == null) {
			throw new ResourceNotFoundException();
		}

		User modifier = this.userDao.findByLogin(modifierLogin);
		if (modifier == null || !event.getCreator().getId().equals(modifier.getId())) {
			throw new AccessDeniedException("");
		}

		event.setName(eventDetails.getName());
		event.setDescription(eventDetails.getDescription());
		event.setStartTime(eventDetails.getStartTime());
		event.setEndTime(eventDetails.getEndTime());
		event.setState(eventDetails.getState().name());
		this.eventDao.updateEvent(event);
	}

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
