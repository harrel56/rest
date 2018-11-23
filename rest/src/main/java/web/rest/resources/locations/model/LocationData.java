package web.rest.resources.locations.model;

import java.io.Serializable;
import java.sql.Timestamp;

import web.rest.resources.users.model.UserData;

@SuppressWarnings("serial")
public class LocationData implements Serializable {

	private final Long id;
	private final UserData creator;
	private final LocationDetailsData locationDetails;
	private final Timestamp createTime;
	private final Timestamp modifyTime;

	public LocationData(Long id, UserData creator, LocationDetailsData locationDetails, Timestamp createTime, Timestamp modifyTime) {
		this.id = id;
		this.creator = creator;
		this.locationDetails = locationDetails;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return this.id;
	}

	public UserData getCreator() {
		return this.creator;
	}

	public LocationDetailsData getLocationDetails() {
		return this.locationDetails;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}
}
