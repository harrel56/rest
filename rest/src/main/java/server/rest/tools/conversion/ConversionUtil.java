package server.rest.tools.conversion;

import hibernate.entities.Attendance;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import server.rest.resources.attendances.model.AttendanceData;
import server.rest.resources.attendances.model.AttendanceDetailsData;
import server.rest.resources.events.model.EventData;
import server.rest.resources.events.model.EventDetailsData;
import server.rest.resources.locations.model.LocationData;
import server.rest.resources.locations.model.LocationDetailsData;
import server.rest.resources.users.model.UserData;
import server.rest.resources.users.model.UserDetailsData;

import java.util.ArrayList;
import java.util.List;

public class ConversionUtil {

    private ConversionUtil() {
    }

    public static List<UserData> toUserDataObjectList(List<User> users) {
        return toUserDataObjectList(users, new DataExpander());
    }

    public static List<UserData> toUserDataObjectList(List<User> users, DataExpander expander) {
        List<UserData> userDatas = new ArrayList<>(users.size());
        for (User user : users) {
            userDatas.add(toDataObject(user, expander));
        }
        return userDatas;
    }

    public static List<LocationData> toLocationDataObjectList(List<Location> locations) {
        return toLocationDataObjectList(locations, new DataExpander());
    }

    public static List<LocationData> toLocationDataObjectList(List<Location> locations, DataExpander expander) {
        List<LocationData> locationDatas = new ArrayList<>(locations.size());
        for (Location location : locations) {
            locationDatas.add(toDataObject(location, expander));
        }
        return locationDatas;
    }

    public static List<EventData> toEventDataObjectList(List<Event> events) {
        return toEventDataObjectList(events, new DataExpander());
    }

    public static List<EventData> toEventDataObjectList(List<Event> events, DataExpander expander) {
        List<EventData> eventDatas = new ArrayList<>(events.size());
        for (Event event : events) {
            eventDatas.add(toDataObject(event, expander));
        }
        return eventDatas;
    }

    public static List<AttendanceData> toAttendanceDataObjectList(List<Attendance> atts) {
        return toAttendanceDataObjectList(atts, new DataExpander());
    }

    public static List<AttendanceData> toAttendanceDataObjectList(List<Attendance> atts, DataExpander expander) {
        List<AttendanceData> attDatas = new ArrayList<>(atts.size());
        for (Attendance att : atts) {
            attDatas.add(toDataObject(att, expander));
        }
        return attDatas;
    }

    @Conversion
    public static UserData toDataObject(User user) {
        return toDataObject(user, new DataExpander());
    }

    public static UserData toDataObject(User user, DataExpander expander) {

        if (user == null) {
            return null;
        }

        UserData result = new UserData(user.getId(), user.getLogin(), user.getCreateTime(), user.getModifyTime(),
                new UserDetailsData(user.getName(), user.getSurname(), user.getLocation(), user.getDateOfBirth()));

        expander.to(UserData.class).from(User.class).expand(result, user);
        return result;
    }

    @Conversion
    public static LocationData toDataObject(Location location) {
        return toDataObject(location, new DataExpander());
    }

    public static LocationData toDataObject(Location location, DataExpander expander) {

        if (location == null) {
            return null;
        }

        LocationData result = new LocationData(location.getId(), null, new LocationDetailsData(location.getName(), location.getDescription(),
                location.getLatitude(), location.getLongitude(), location.getState()), location.getCreateTime(), location.getModifyTime());

        expander.to(LocationData.class).from(Location.class).expand(result, location);
        return result;
    }

    @Conversion
    public static EventData toDataObject(Event event) {
        return toDataObject(event, new DataExpander());
    }

    public static EventData toDataObject(Event event, DataExpander expander) {

        if (event == null) {
            return null;
        }

        EventData result = new EventData(event.getId(), null, null,
                new EventDetailsData(event.getName(), event.getDescription(), event.getStartTime(), event.getEndTime(), event.getState()),
                event.getCreateTime(), event.getModifyTime());

        expander.to(EventData.class).from(Event.class).expand(result, event);
        return result;
    }

    @Conversion
    public static AttendanceData toDataObject(Attendance att) {
        return toDataObject(att, new DataExpander());
    }

    public static AttendanceData toDataObject(Attendance att, DataExpander expander) {

        if (att == null) {
            return null;
        }

        AttendanceData result = new AttendanceData(att.getId(), null, null, new AttendanceDetailsData(att.getType()), att.getCreateTime(),
                att.getModifyTime());

        expander.to(AttendanceData.class).from(Attendance.class).expand(result, att);
        return result;
    }

}
