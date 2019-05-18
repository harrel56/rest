package server.rest.resources.attendances.model;

import hibernate.enums.AttendanceType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
