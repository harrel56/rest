package web.rest.resources.attendances.model;

import java.io.Serializable;
import java.sql.Timestamp;

import web.rest.resources.events.model.EventData;
import web.rest.resources.users.model.UserData;

@SuppressWarnings("serial")
public class AttendanceData implements Serializable {

	private final Long id;
	private final UserData user;
	private final EventData event;
	private final AttendanceDetailsData attendanceDetails;
	private final Timestamp createTime;
	private final Timestamp modifyTime;

	public AttendanceData(Long id, UserData user, EventData event, AttendanceDetailsData attendanceDetails, Timestamp createTime,
			Timestamp modifyTime) {
		this.id = id;
		this.user = user;
		this.event = event;
		this.attendanceDetails = attendanceDetails;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return this.id;
	}

	public UserData getUser() {
		return this.user;
	}

	public EventData getEvent() {
		return this.event;
	}

	public AttendanceDetailsData getType() {
		return this.attendanceDetails;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

}
