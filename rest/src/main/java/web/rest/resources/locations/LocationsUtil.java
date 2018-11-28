package web.rest.resources.locations;

import static web.rest.tools.conversion.ConversionUtil.toDataObject;
import static web.rest.tools.conversion.ConversionUtil.toEventDataObjectList;
import static web.rest.tools.conversion.ConversionUtil.toLocationDataObjectList;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hibernate.dao.EventDao;
import hibernate.dao.LocationDao;
import hibernate.dao.UserDao;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import hibernate.enums.State;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.ImmutableDataModificationException;
import web.rest.resources.events.EventsUtil;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.locations.model.LocationData;
import web.rest.resources.locations.model.LocationDetailsData;
import web.rest.tools.conversion.DataExpander;

@Service
public class LocationsUtil {

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private EventsUtil eventsUtil;

	public List<LocationData> getLocations() {
		return toLocationDataObjectList(this.locationDao.getLocations());
	}

	public List<LocationData> getLocations(SearchParams<Location> searchParams, SortParams<Location> sortParams, DataExpander expander) {
		return toLocationDataObjectList(this.locationDao.getLocations(searchParams, sortParams), expander);
	}

	public LocationData getLocation(Long id) {
		return toDataObject(this.locationDao.findLocationById(id));
	}

	public List<EventData> getLocationEvents(Long id) {

		Location location = this.locationDao.findLocationById(id);
		if (location == null) {
			throw new ResourceNotFoundException();
		}

		return toEventDataObjectList(location.getEvents());
	}

	public LocationData createLocation(LocationDetailsData locationDetails, String creatorLogin) {

		User creator = this.userDao.findByLogin(creatorLogin);

		if (creator != null) {

			Location location = new Location();
			location.setCreator(creator);
			location.setName(locationDetails.getName());
			location.setDescription(locationDetails.getDescription());
			location.setLatitude(locationDetails.getLatitude());
			location.setLongitude(locationDetails.getLongitude());
			location.setState(locationDetails.getState());

			this.locationDao.addLocation(location);
			return toDataObject(location);
		} else {
			throw new AccessDeniedException("Location creator not found!");
		}
	}

	@Transactional
	public void updateLocation(Long id, LocationDetailsData locationDetails, String modifierLogin) {

		Location location = this.locationDao.findLocationById(id);
		if (location == null) {
			throw new ResourceNotFoundException();
		}

		final User modifier = this.userDao.findByLogin(modifierLogin);
		if (modifier == null || !location.getCreator().getId().equals(modifier.getId())) {
			throw new AccessDeniedException("Only location creator can modify it!");
		}

		if (location.getState() == State.DELETED) {
			throw new UnsupportedOperationException();
		}

		if (!location.getLatitude().equals(locationDetails.getLatitude()) || !location.getLongitude().equals(locationDetails.getLongitude())) {
			throw new ImmutableDataModificationException("Latitude and longitude cannot be modified!");
		}

		if (locationDetails.getState() == State.DELETED) {

			/* Check if there are any non modifier events attached to this location */
			List<Event> events = location.getEvents();
			boolean usedBySomeone = events.stream().anyMatch(new Predicate<Event>() {

				@Override
				public boolean test(Event event) {
					return !event.getCreator().getId().equals(modifier.getId());
				}
			});

			if (usedBySomeone) {
				throw new UnsupportedOperationException();
			}

			/* If location is deleted, also mark all attached events as deleted */
			for (Event event : events) {
				event.setState(State.DELETED);
				this.eventDao.updateEvent(event);
			}
		}

		location.setName(locationDetails.getName());
		location.setDescription(locationDetails.getDescription());
		location.setState(locationDetails.getState());

		this.locationDao.updateLocation(location);
	}

	public EventData createLocationEvent(Long id, EventDetailsData eventDetails, String creatorLogin) {

		return this.eventsUtil.createEvent(this.locationDao.findLocationById(id), eventDetails, creatorLogin);
	}
}
