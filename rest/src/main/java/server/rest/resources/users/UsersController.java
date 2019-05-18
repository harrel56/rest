package server.rest.resources.users;

import hibernate.entities.User;
import hibernate.search.UserSearchParams;
import hibernate.sort.SortParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.rest.resources.attendances.model.AttendanceData;
import server.rest.resources.events.model.EventData;
import server.rest.resources.locations.model.LocationData;
import server.rest.resources.pagination.PaginationParams;
import server.rest.resources.pagination.PaginationWrapper;
import server.rest.resources.users.model.UserData;
import server.rest.resources.users.model.UserDetailsData;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersUtil usersUtil;

    @GetMapping({"", "/"})
    public PaginationWrapper<UserData> getUsers(@ModelAttribute PaginationParams paginationParams, @RequestParam(name = "login", required = false) String login,
                                                @RequestParam(name = "name", required = false) String name, @RequestParam(name = "surname", required = false) String surname,
                                                @RequestParam(name = "location", required = false) String location, @RequestParam(name = "sort", required = false) String[] sorts) {

        UserSearchParams searchParams = new UserSearchParams(login, name, surname, location);
        Long totalCount = this.usersUtil.getUsersCount(searchParams);

        return PaginationWrapper.wrap(this.usersUtil.getUsers(new UserSearchParams(login, name, surname, location),
                new SortParams<User>(User.class, sorts), paginationParams), paginationParams, totalCount);
    }

    @GetMapping("/{login}")
    public UserData getUser(@PathVariable String login) {

        return this.usersUtil.getUserByLogin(login);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{login}")
    public ResponseEntity<Void> updateUser(@PathVariable String login, @RequestBody UserDetailsData userDetails) {

        if (login.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            this.usersUtil.updateUserDetails(login, userDetails);
        } else {
            throw new AccessDeniedException("Invalid token for request operation!");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{login}/locations")
    public List<LocationData> getUserLocations(@PathVariable String login) {

        return this.usersUtil.getUserLocations(login);
    }

    @GetMapping("/{login}/events")
    public List<EventData> getUserEvents(@PathVariable String login) {

        return this.usersUtil.getUserEvents(login);
    }

    @GetMapping("/{login}/attendances")
    public List<AttendanceData> getUserAttendances(@PathVariable String login) {

        return this.usersUtil.getUserAttendances(login);
    }
}
