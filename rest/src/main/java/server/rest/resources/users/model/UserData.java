package server.rest.resources.users.model;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class UserData implements Serializable {

    private final Long id;
    private final String login;
    private final Timestamp createTime;
    private final Timestamp modifyTime;
    private final UserDetailsData userDetails;

    public UserData(Long id, String login, Timestamp createTime, Timestamp modifyTime, UserDetailsData userDetails) {
        this.id = id;
        this.login = login;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.userDetails = userDetails;
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public UserDetailsData getUserDetails() {
        return this.userDetails;
    }
}
