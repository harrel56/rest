package hibernate.entities;

import hibernate.enums.AttendanceType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import server.rest.tools.conversion.Expandable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Entity
@Table(name = "ATTENDANCE")
public class Attendance implements Serializable {

    private Long id;
    private User user;
    private Event event;
    private AttendanceType type;
    private Timestamp createTime;
    private Timestamp modifyTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Expandable(name = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Expandable(name = "event")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 2000000000)
    public AttendanceType getType() {
        return this.type;
    }

    public void setType(AttendanceType type) {
        this.type = type;
    }

    @CreationTimestamp
    @Column(name = "CREATE_TIME", nullable = false, length = 2000000000)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @UpdateTimestamp
    @Column(name = "MODIFY_TIME", nullable = false, length = 2000000000)
    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

}
