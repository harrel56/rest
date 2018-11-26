package web.rest.resources.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import hibernate.dao.AttendanceDao;
import hibernate.dao.EventDao;
import hibernate.dao.UserDao;
import hibernate.entities.Attendance;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.attendances.model.AttendanceData;
import web.rest.resources.attendances.model.AttendanceDetailsData;
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
	private AttendanceDao attendanceDao;

	@Autowired
	private LocationsUtil locationsUtil;

	@Autowired
	private UsersUtil usersUtil;

	public List<EventData> getEvents() {
		return this.toDataObjectList(this.eventDao.getEvents());
	}

	public List<EventData> getEvents(SearchParams<Event> searchParams, SortParams<Event> sortParams) {
		return this.toDataObjectList(this.eventDao.getEvents(searchParams, sortParams));
	}

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
		event.setState(eventDetails.getState());

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
		event.setState(eventDetails.getState());
		this.eventDao.updateEvent(event);
	}

	public AttendanceData createEventAttendance(Long id, String userId, AttendanceDetailsData attendanceDetails) {

		Event event = this.eventDao.findEventById(id);
		if (event == null) {
			throw new ResourceNotFoundException();
		}

		User user = this.userDao.findByLogin(userId);
		if (user == null || !event.getCreator().getId().equals(user.getId())) {
			throw new AccessDeniedException("");
		}

		Attendance attendance = new Attendance();
		attendance.setEvent(event);
		attendance.setUser(user);
		attendance.setType(attendanceDetails.getType());
		this.attendanceDao.addAttendance(attendance);

		return this.toDataObject(attendance);
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
				new EventDetailsData(event.getName(), event.getDescription(), event.getStartTime(), event.getEndTime(), event.getState()),
				event.getCreateTime(), event.getModifyTime());
	}

//	public List<AttendanceData> toDataObjectList(List<Attendance> atts) {
//		List<AttendanceData> attDatas = new ArrayList<>(atts.size());
//		for (Attendance att : atts) {
//			attDatas.add(this.toDataObject(att));
//		}
//		return attDatas;
//	}

	public AttendanceData toDataObject(Attendance att) {
		return new AttendanceData(att.getId(), this.usersUtil.toDataObject(att.getUser()), this.toDataObject(att.getEvent()),
				new AttendanceDetailsData(att.getType()), att.getCreateTime(), att.getModifyTime());
	}
}
