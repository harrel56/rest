package web.rest.resources.locations;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hibernate.entities.Location;
import hibernate.search.LocationSearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.locations.model.LocationDetailsData;
import web.rest.tools.validation.ValidationUtil;

@RestController
@RequestMapping("/locations")
public class LocationsController {

	@SuppressWarnings("unused")
	private static transient final Logger logger = LoggerFactory.getLogger(LocationsController.class);

	@Autowired
	LocationsUtil locationsUtil;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public List<LocationData> getLocations(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@RequestParam(name = "name", required = false) String name, @RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "state", required = false) String state, @RequestParam(name = "latitude", required = false) Double latitude,
			@RequestParam(name = "longitude", required = false) Double longitude, @RequestParam(name = "radius", required = false) Double radius,
			@RequestParam(name = "sort", required = false) String[] sorts) {

		return this.locationsUtil.getLocations(new LocationSearchParams(name, description, state, latitude, longitude, radius),
				new SortParams<Location>(Location.class, sorts));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public LocationData getLocation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id) {

		return this.locationsUtil.getLocation(id);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
	public ResponseEntity<LocationData> createLocation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@Valid @RequestBody LocationDetailsData location, BindingResult validation) {

		ValidationUtil.handleValidation(validation);

		String creatorLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>(this.locationsUtil.createLocation(location, creatorLogin), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateLocation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id,
			@Valid @RequestBody LocationDetailsData location, BindingResult validation) {

		ValidationUtil.handleValidation(validation);

		String modifierLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		this.locationsUtil.updateLocation(id, location, modifierLogin);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
	public List<EventData> getEventsByLocation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id) {

		return this.locationsUtil.getLocationEvents(id);
	}

	@PreAuthorize("hasAuthority('USER')")
	@RequestMapping(value = "/{id}/events", method = RequestMethod.POST)
	public EventData createEventByLocation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id,
			@Valid @RequestBody EventDetailsData eventDetails, BindingResult validation) {

		ValidationUtil.handleValidation(validation);

		return this.locationsUtil.createLocationEvent(id, eventDetails, SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
