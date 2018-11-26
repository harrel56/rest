package web.rest.tools.conversion;

import java.util.ArrayList;
import java.util.List;

import hibernate.entities.Attendance;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import web.rest.resources.attendances.model.AttendanceData;
import web.rest.resources.attendances.model.AttendanceDetailsData;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.locations.model.LocationDetailsData;
import web.rest.resources.users.model.UserData;
import web.rest.resources.users.model.UserDetailsData;

public class ConversionUtil {

	private ConversionUtil() {
	}

	public static List<UserData> toUserDataObjectList(List<User> users) {
		List<UserData> userDatas = new ArrayList<>(users.size());
		for (User user : users) {
			userDatas.add(toDataObject(user));
		}
		return userDatas;
	}

	public static List<LocationData> toLocationDataObjectList(List<Location> locations) {
		List<LocationData> locationDatas = new ArrayList<>(locations.size());
		for (Location location : locations) {
			locationDatas.add(toDataObject(location));
		}
		return locationDatas;
	}

	public static List<EventData> toEventDataObjectList(List<Event> events) {
		List<EventData> locationDatas = new ArrayList<>(events.size());
		for (Event event : events) {
			locationDatas.add(toDataObject(event));
		}
		return locationDatas;
	}

	public static List<AttendanceData> toAttendanceDataObjectList(List<Attendance> atts) {
		List<AttendanceData> attDatas = new ArrayList<>(atts.size());
		for (Attendance att : atts) {
			attDatas.add(toDataObject(att));
		}
		return attDatas;
	}

	public static UserData toDataObject(User user) {
		return new UserData(user.getId(), user.getLogin(), user.getCreateTime(), user.getModifyTime(),
				new UserDetailsData(user.getName(), user.getSurname(), user.getLocation(), user.getDateOfBirth()));
	}

	public static LocationData toDataObject(Location location) {
		return new LocationData(location.getId(), toDataObject(location.getCreator()), new LocationDetailsData(location.getName(),
				location.getDescription(), location.getLatitude(), location.getLongitude(), location.getState()), location.getCreateTime(),
				location.getModifyTime());
	}

	public static EventData toDataObject(Event event) {
		return new EventData(event.getId(), toDataObject(event.getLocation()), toDataObject(event.getCreator()),
				new EventDetailsData(event.getName(), event.getDescription(), event.getStartTime(), event.getEndTime(), event.getState()),
				event.getCreateTime(), event.getModifyTime());
	}

	public static AttendanceData toDataObject(Attendance att) {
		return new AttendanceData(att.getId(), toDataObject(att.getUser()), toDataObject(att.getEvent()), new AttendanceDetailsData(att.getType()),
				att.getCreateTime(), att.getModifyTime());
	}

}
