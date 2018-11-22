package web.rest.resources.events.model;

import java.io.Serializable;
import java.sql.Timestamp;

import web.rest.resources.locations.model.LocationData;
import web.rest.resources.users.model.UserData;

@SuppressWarnings("serial")
public class EventData implements Serializable {

	public static enum State {
		ACTIVE, DELETED
	}

	private final Long id;
	private final LocationData location;
	private final UserData creator;
	private final String name;
	private final String description;
	private final Timestamp startTime;
	private final Timestamp endTime;
	private final Timestamp createTime;
	private final Timestamp modifyTime;
	private final State state;

	public EventData(Long id, LocationData location, UserData user, String name, String description, Timestamp startTime, Timestamp endTime,
			Timestamp createTime, Timestamp modifyTime, State state) {
		this.id = id;
		this.location = location;
		this.creator = user;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	public Long getId() {
		return this.id;
	}

	public LocationData getLocation() {
		return this.location;
	}

	public UserData getUser() {
		return this.creator;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public State getState() {
		return this.state;
	}
}
