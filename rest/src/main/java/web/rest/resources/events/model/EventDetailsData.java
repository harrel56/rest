package web.rest.resources.events.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hibernate.enums.State;

@SuppressWarnings("serial")
public class EventDetailsData implements Serializable {

	@NotNull(message = "validation.eventDetails.name")
	private final String name;
	private final String description;

	@NotNull(message = "validation.eventDetails.startTime")
	private final Timestamp startTime;
	private final Timestamp endTime;

	@NotNull(message = "validation.eventDetails.state")
	private final State state;

	public EventDetailsData(String name, String description, Timestamp startTime, Timestamp endTime, State state) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public State getState() {
		return this.state;
	}

	@JsonIgnore
	@AssertTrue(message = "validation.eventDetails.startTime")
	public boolean isStartTimeValid() {
		return this.startTime.after(new Date());
	}

	@JsonIgnore
	@AssertTrue(message = "validation.eventDetails.endTime")
	public boolean isEndTimeValid() {
		return this.endTime == null || (this.endTime.after(new Date()) && this.endTime.after(this.startTime));
	}

}
