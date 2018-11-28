package hibernate.entities;
// Generated Nov 22, 2018 9:36:39 PM by Hibernate Tools 5.2.11.Final

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import hibernate.enums.State;
import web.rest.tools.conversion.Expandable;

/**
 * Event generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EVENT")
public class Event implements Serializable {

	private Long id;
	private Location location;
	private User creator;
	private String name;
	private String description;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private State state;

	private List<Attendance> attendances = new ArrayList<>(0);

	public Event() {
	}

	public Event(Long id, Location location, String name, Timestamp startTime, Timestamp createTime, Timestamp modifyTime, State state) {
		this.id = id;
		this.location = location;
		this.name = name;
		this.startTime = startTime;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	public Event(Long id, Location location, User creator, String name, String description, Timestamp startTime, Timestamp endTime,
			Timestamp createTime, Timestamp modifyTime, State state) {
		this.id = id;
		this.location = location;
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Expandable(name = "location")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATION_ID", nullable = false)
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Expandable(name = "creator")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR_USER_ID")
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Column(name = "NAME", nullable = false, length = 2000000000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 2000000000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "START_TIME", nullable = false, length = 2000000000)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 2000000000)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = false, length = 2000000000)
	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
	public List<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

}
