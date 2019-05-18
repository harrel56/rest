package server.rest.resources.attendances.model;

import server.rest.resources.events.model.EventData;
import server.rest.resources.users.model.UserData;
import server.rest.tools.conversion.Expandable;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class AttendanceData implements Serializable {

    private Long id;
    private UserData user;
    private EventData event;
    private AttendanceDetailsData attendanceDetails;
    private Timestamp createTime;
    private Timestamp modifyTime;

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

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getUser() {
        return this.user;
    }

    @Expandable(name = "user")
    public void setUser(UserData user) {
        this.user = user;
    }

    public EventData getEvent() {
        return this.event;
    }

    @Expandable(name = "event")
    public void setEvent(EventData event) {
        this.event = event;
    }

    public AttendanceDetailsData getAttendanceDetails() {
        return this.attendanceDetails;
    }

    public void setAttendanceDetails(AttendanceDetailsData attendanceDetails) {
        this.attendanceDetails = attendanceDetails;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

}
