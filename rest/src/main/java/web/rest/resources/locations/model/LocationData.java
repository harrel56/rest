package web.rest.resources.locations.model;

import java.io.Serializable;
import java.sql.Timestamp;

import web.rest.resources.users.model.UserData;

@SuppressWarnings("serial")
public class LocationData implements Serializable {

	public static enum State {
		ACTIVE, DELETED
	}

	private final Long id;
	private final UserData creator;
	private final String name;
	private final String description;
	private final Double latitude;
	private final Double longitude;
	private final Timestamp createTime;
	private final Timestamp modifyTime;
	private final State state;

	public LocationData(Long id, UserData creator, String name, String description, Double latitude, Double longitude, Timestamp createTime,
			Timestamp modifyTime, State state) {
		this.id = id;
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	public Long getId() {
		return this.id;
	}

	public UserData getCreator() {
		return this.creator;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
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
