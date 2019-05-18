package server.rest.resources.locations;

import hibernate.dao.EventDao;
import hibernate.dao.LocationDao;
import hibernate.dao.UserDao;
import hibernate.entities.Event;
import hibernate.entities.Location;
import hibernate.entities.User;
import hibernate.enums.State;
import hibernate.search.SearchParams;
import hibernate.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.rest.resources.ImmutableDataModificationException;
import server.rest.resources.events.EventsUtil;
import server.rest.resources.events.model.EventData;
import server.rest.resources.events.model.EventDetailsData;
import server.rest.resources.locations.model.LocationData;
import server.rest.resources.locations.model.LocationDetailsData;
import server.rest.resources.pagination.PaginationParams;
import server.rest.tools.conversion.DataExpander;

import java.util.List;

import static server.rest.tools.conversion.ConversionUtil.*;

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

    public Long getLocationsCount(SearchParams<Location> searchParams) {
        return this.locationDao.getLocationsCount(searchParams);
    }

    public List<LocationData> getLocations(SearchParams<Location> searchParams, SortParams<Location> sortParams, DataExpander expander,
                                           PaginationParams paginationParams) {
        return toLocationDataObjectList(this.locationDao.getLocations(searchParams, sortParams, paginationParams), expander);
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

            if (events.stream().anyMatch(e -> !e.getCreator().getId().equals(modifier.getId()))) {
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
