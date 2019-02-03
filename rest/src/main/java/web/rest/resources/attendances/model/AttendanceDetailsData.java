package web.rest.resources.attendances.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import hibernate.enums.AttendanceType;

@SuppressWarnings("serial")
public class AttendanceDetailsData implements Serializable {

	@NotNull(message = "validation.attendanceDetails.type")
	private AttendanceType type;

	public AttendanceDetailsData(AttendanceType type) {
		this.type = type;
	}

	public AttendanceType getType() {
		return this.type;
	}

}
