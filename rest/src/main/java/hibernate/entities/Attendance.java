package hibernate.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import hibernate.enums.AttendanceType;
import web.rest.tools.conversion.Expandable;

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
