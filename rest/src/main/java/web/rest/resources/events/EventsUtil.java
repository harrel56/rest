package web.rest.resources.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.dao.EventDao;
import hibernate.entities.Event;
import web.rest.resources.events.model.EventData;
import web.rest.resources.locations.LocationsUtil;
import web.rest.resources.users.UsersUtil;

@Service
public class EventsUtil {

	@Autowired
	private EventDao eventsDao;

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

	private List<EventData> toDataObjectList(List<Event> events) {
		List<EventData> locationDatas = new ArrayList<>(events.size());
		for (Event event : events) {
			locationDatas.add(this.toDataObject(event));
		}
		return locationDatas;
	}

	private EventData toDataObject(Event event) {
		return new EventData(event.getId(), this.locationsUtil.toDataObject(event.getLocation()), this.usersUtil.toDataObject(event.getCreator()),
				event.getName(), event.getDescription(), event.getStartTime(), event.getEndTime(), event.getCreateTime(), event.getModifyTime(),
				EventData.State.valueOf(event.getState()));
	}
}
