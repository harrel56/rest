package server.rest.resources.locations.model;

import server.rest.resources.users.model.UserData;
import server.rest.tools.conversion.Expandable;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class LocationData implements Serializable {

    private Long id;
    private UserData creator;
    private LocationDetailsData locationDetails;
    private Timestamp createTime;
    private Timestamp modifyTime;

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

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getCreator() {
        return this.creator;
    }

    @Expandable(name = "creator")
    public void setCreator(UserData creator) {
        this.creator = creator;
    }

    public LocationDetailsData getLocationDetails() {
        return this.locationDetails;
    }

    public void setLocationDetails(LocationDetailsData locationDetails) {
        this.locationDetails = locationDetails;
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
