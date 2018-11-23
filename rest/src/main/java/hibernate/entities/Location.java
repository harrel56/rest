package hibernate.entities;
// Generated Nov 22, 2018 9:36:39 PM by Hibernate Tools 5.2.11.Final

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * Location generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {

	private Long id;
	private User creator;
	private String name;
	private String description;
	private Double latitude;
	private Double longitude;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String state;
	private List<Event> events = new ArrayList<Event>(0);

	public Location() {
	}

	public Location(Long id, String name, Double latitude, Double longitude, Timestamp createTime, Timestamp modifyTime, String state) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
	}

	public Location(Long id, User creator, String name, String description, Double latitude, Double longitude, Timestamp createTime,
			Timestamp modifyTime, String state, List<Event> events) {
		this.id = id;
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.state = state;
		this.events = events;
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

	@Column(name = "LATITUDE", nullable = false)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "LONGITUDE", nullable = false)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	@Column(name = "STATE", nullable = false, length = 2000000000)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
