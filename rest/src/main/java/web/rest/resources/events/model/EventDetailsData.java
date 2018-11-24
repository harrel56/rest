package web.rest.resources.events.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class EventDetailsData implements Serializable {

	public static enum State {
		ACTIVE, DELETED
	}

	@NotNull
	private final String name;
	private final String description;

	@NotNull
	private final Timestamp startTime;
	private final Timestamp endTime;

	@NotNull
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
	@AssertTrue
	public boolean isStartTimeValid() {
		return this.startTime.after(new Date());
	}

	@JsonIgnore
	@AssertTrue
	public boolean isEndTimeValid() {
		return this.endTime == null || (this.endTime.after(new Date()) && this.endTime.after(this.startTime));
	}

}
