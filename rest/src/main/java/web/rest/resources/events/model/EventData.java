package web.rest.resources.events.model;

import java.io.Serializable;
import java.sql.Timestamp;

import web.rest.resources.locations.model.LocationData;
import web.rest.resources.users.model.UserData;

@SuppressWarnings("serial")
public class EventData implements Serializable {

	private final Long id;
	private final LocationData location;
	private final UserData creator;
	private final EventDetailsData eventDetails;
	private final Timestamp createTime;
	private final Timestamp modifyTime;

	public EventData(Long id, LocationData location, UserData user, EventDetailsData eventDetails, Timestamp createTime, Timestamp modifyTime) {
		this.id = id;
		this.location = location;
		this.creator = user;
		this.eventDetails = eventDetails;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return this.id;
	}

	public LocationData getLocation() {
		return this.location;
	}

	public UserData getCreator() {
		return this.creator;
	}

	public EventDetailsData getEventDetails() {
		return this.eventDetails;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}
}
