package web.rest.resources.locations.model;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class LocationDetailsData implements Serializable {

	public static enum State {
		ACTIVE, DELETED
	}

	@NotNull(message = "validation.locationDetails.name")
	private final String name;
	private final String description;

	@NotNull(message = "validation.locationDetails.latitude")
	private final Double latitude;

	@NotNull(message = "validation.locationDetails.longitude")
	private final Double longitude;

	@NotNull(message = "validation.locationDetails.state")
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

	@JsonIgnore
	@AssertTrue(message = "validation.locationDetails.latitude")
	public boolean isLatitudeValid() {
		return this.latitude >= -90.0 && this.latitude <= 90.0;
	}

	@JsonIgnore
	@AssertTrue(message = "validation.locationDetails.longitude")
	public boolean isLongitudeValid() {
		return this.longitude >= -180.0 && this.longitude <= 180.0;
	}

}