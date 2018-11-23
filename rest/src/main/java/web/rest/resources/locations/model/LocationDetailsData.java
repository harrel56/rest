package web.rest.resources.locations.model;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class LocationDetailsData implements Serializable {

	public static enum State {
		ACTIVE, DELETED
	}

	@NotNull
	private final String name;
	private final String description;

	@NotNull
	private final Double latitude;

	@NotNull
	private final Double longitude;

	@NotNull
	private final State state;

	public LocationDetailsData(String name, String description, Double latitude, Double longitude, State state) {
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public State getState() {
		return this.state;
	}

	@AssertTrue
	public boolean validateLatitude() {
		return this.latitude >= -90.0 && this.latitude <= 90.0;
	}

	@AssertTrue
	public boolean validateLongitude() {
		return this.longitude >= -180.0 && this.longitude <= 180.0;
	}

}