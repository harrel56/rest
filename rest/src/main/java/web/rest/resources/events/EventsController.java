package web.rest.resources.events;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hibernate.entities.Event;
import hibernate.search.EventSearchParams;
import hibernate.sort.SortParams;
import web.rest.resources.attendances.model.AttendanceData;
import web.rest.resources.attendances.model.AttendanceDetailsData;
import web.rest.resources.events.model.EventData;
import web.rest.resources.events.model.EventDetailsData;
import web.rest.resources.pagination.Paginated;
import web.rest.resources.pagination.PaginationParams;
import web.rest.tools.conversion.DataExpander;
import web.rest.tools.validation.ValidationUtil;

@RestController
@RequestMapping("/events")
public class EventsController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

	@Autowired
	private EventsUtil eventsUtil;

	@Paginated
	@GetMapping({ "", "/" })
	public ResponseEntity<List<EventData>> getEvents(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@ModelAttribute PaginationParams paginationParams, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "description", required = false) String description, @RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "startTimeGt", required = false) Timestamp startTimeGt,
			@RequestParam(name = "startTimeLt", required = false) Timestamp startTimeLt,
			@RequestParam(name = "endTimeGt", required = false) Timestamp endTimeGt,
			@RequestParam(name = "endTimeLt", required = false) Timestamp endTimeLt, @RequestParam(name = "sort", required = false) String[] sorts,
			@RequestParam(name = "expand", required = false) String[] expands) {

		EventSearchParams searchParams = new EventSearchParams(name, description, state, startTimeGt, startTimeLt, endTimeGt, endTimeLt);
		Long total = this.eventsUtil.getEventsCount(searchParams);

		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("total", total.toString());

		return new ResponseEntity<>(
				this.eventsUtil.getEvents(searchParams, new SortParams<Event>(Event.class, sorts), new DataExpander(expands), paginationParams),
				headers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public EventData getEvent(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id) {

		return this.eventsUtil.getEvent(id);
	}

	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateEvent(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale, @PathVariable Long id,
			@Valid @RequestBody EventDetailsData eventDetails, BindingResult validation) {

		ValidationUtil.handleValidation(validation);

		String modifierLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		this.eventsUtil.updateEvent(id, eventDetails, modifierLogin);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Attendances operations */
	@GetMapping("/{id}/attendances")
	public List<AttendanceData> getEventAttendances(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@PathVariable Long id, @RequestParam(name = "expand", required = false) String[] expands) {

		return this.eventsUtil.getEventAttendances(id, new DataExpander(expands));
	}

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/{id}/attendances")
	public ResponseEntity<AttendanceData> createEventAttendance(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
			@PathVariable Long id, @Valid @RequestBody AttendanceDetailsData attendanceDetails, BindingResult validation) {

		ValidationUtil.handleValidation(validation);

		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>(this.eventsUtil.createEventAttendance(id, userLogin, attendanceDetails), HttpStatus.CREATED);
	}

}
