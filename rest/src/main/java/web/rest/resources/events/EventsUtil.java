package web.rest.resources.events;

import static web.rest.tools.conversion.ConversionUtil.toAttendanceDataObjectList;
import static web.rest.tools.conversion.ConversionUtil.toDataObject;
import static web.rest.tools.conversion.ConversionUtil.toEventDataObjectList;

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
import web.rest.resources.pagination.PaginationParams;
import web.rest.tools.conversion.DataExpander;

@Service
public class EventsUtil {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AttendanceDao attendanceDao;

	public List<EventData> getEvents() {
		return toEventDataObjectList(this.eventDao.getEvents());
	}

	public Long getEventsCount(SearchParams<Event> searchParams) {
		return this.eventDao.getEventsCount(searchParams);
	}

	public List<EventData> getEvents(SearchParams<Event> searchParams, SortParams<Event> sortParams, DataExpander expander,
			PaginationParams paginationParams) {
		return toEventDataObjectList(this.eventDao.getEvents(searchParams, sortParams, paginationParams), expander);
	}

	public EventData getEvent(Long id) {
		return toDataObject(this.eventDao.findEventById(id));
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
		return toDataObject(event);
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

	public List<AttendanceData> getEventAttendances(Long id, DataExpander expander) {

		Event event = this.eventDao.findEventById(id);
		if (event == null) {
			throw new ResourceNotFoundException();
		}

		return toAttendanceDataObjectList(event.getAttendances(), expander);
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

		return toDataObject(attendance);
	}
}
