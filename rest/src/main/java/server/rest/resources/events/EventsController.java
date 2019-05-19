package server.rest.resources.events;

import hibernate.entities.Event;
import hibernate.search.EventSearchParams;
import hibernate.sort.SortParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import server.rest.resources.attendances.model.AttendanceData;
import server.rest.resources.attendances.model.AttendanceDetailsData;
import server.rest.resources.events.model.EventData;
import server.rest.resources.events.model.EventDetailsData;
import server.rest.resources.pagination.PaginationParams;
import server.rest.resources.pagination.PaginationWrapper;
import server.rest.tools.conversion.DataExpander;
import server.rest.tools.validation.ValidationUtil;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private EventsUtil eventsUtil;

    @GetMapping({"", "/"})
    public PaginationWrapper<EventData> getEvents(@ModelAttribute PaginationParams paginationParams, @RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "description", required = false) String description, @RequestParam(name = "state", required = false) String state,
                                                  @RequestParam(name = "startTimeGt", required = false) Timestamp startTimeGt,
                                                  @RequestParam(name = "startTimeLt", required = false) Timestamp startTimeLt,
                                                  @RequestParam(name = "endTimeGt", required = false) Timestamp endTimeGt,
                                                  @RequestParam(name = "endTimeLt", required = false) Timestamp endTimeLt, @RequestParam(name = "sort", required = false) String[] sorts,
                                                  @RequestParam(name = "expand", required = false) String[] expands) {

        EventSearchParams searchParams = new EventSearchParams(name, description, state, startTimeGt, startTimeLt, endTimeGt, endTimeLt);
        Long totalCount = this.eventsUtil.getEventsCount(searchParams);

        return PaginationWrapper.wrap(
                this.eventsUtil.getEvents(searchParams, new SortParams<Event>(Event.class, sorts), new DataExpander(expands), paginationParams),
                paginationParams, totalCount);
    }

    @GetMapping("/{id}")
    public EventData getEvent(@PathVariable Long id) {

        return this.eventsUtil.getEvent(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable Long id,
                                            @Valid @RequestBody EventDetailsData eventDetails, BindingResult validation) {

        ValidationUtil.handleValidation(validation);

        String modifierLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        this.eventsUtil.updateEvent(id, eventDetails, modifierLogin);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Attendances operations */
    @GetMapping("/{id}/attendances")
    public List<AttendanceData> getEventAttendances(@PathVariable Long id, @RequestParam(name = "expand", required = false) String[] expands) {

        return this.eventsUtil.getEventAttendances(id, new DataExpander(expands));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}/attendances")
    public ResponseEntity<AttendanceData> createEventAttendance(@PathVariable Long id, @Valid @RequestBody AttendanceDetailsData attendanceDetails, BindingResult validation) {

        ValidationUtil.handleValidation(validation);

        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(this.eventsUtil.createEventAttendance(id, userLogin, attendanceDetails), HttpStatus.CREATED);
    }

}
