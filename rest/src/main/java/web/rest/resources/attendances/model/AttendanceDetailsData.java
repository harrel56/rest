package web.rest.resources.attendances.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class AttendanceDetailsData implements Serializable {

	public static enum Type {
		GOING, INTERESTED, FOLLOWING
	}

	@NotNull(message = "validation.attendanceDetails.type")
	private Type type;

	public AttendanceDetailsData() {
	}

	public AttendanceDetailsData(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

}
