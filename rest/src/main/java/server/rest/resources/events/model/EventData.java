package server.rest.resources.events.model;

import server.rest.resources.locations.model.LocationData;
import server.rest.resources.users.model.UserData;
import server.rest.tools.conversion.Expandable;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class EventData implements Serializable {

    private Long id;
    private LocationData location;
    private UserData creator;
    private EventDetailsData eventDetails;
    private Timestamp createTime;
    private Timestamp modifyTime;

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

    public void setId(Long id) {
        this.id = id;
    }

    public LocationData getLocation() {
        return this.location;
    }

    @Expandable(name = "location")
    public void setLocation(LocationData location) {
        this.location = location;
    }

    public UserData getCreator() {
        return this.creator;
    }

    @Expandable(name = "creator")
    public void setCreator(UserData creator) {
        this.creator = creator;
    }

    public EventDetailsData getEventDetails() {
        return this.eventDetails;
    }

    public void setEventDetails(EventDetailsData eventDetails) {
        this.eventDetails = eventDetails;
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
