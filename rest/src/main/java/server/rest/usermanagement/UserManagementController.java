package server.rest.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.rest.usermanagement.activation.ResendActivationResponseData;
import server.rest.usermanagement.activation.UserActivationResponseData;
import server.rest.usermanagement.passwordchange.PasswordChangeRequestData;
import server.rest.usermanagement.passwordchange.PasswordChangeResponseData;
import server.rest.usermanagement.register.UserRegistrationRequestData;
import server.rest.usermanagement.register.UserRegistrationResponseData;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("api/user-management")
public class UserManagementController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    private UserManagementUtil userManagementUtil;

    @Autowired
    private UserManagementResponseCreator responseCreator;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseData> register(Locale locale,
                                                                 @Valid @RequestBody UserRegistrationRequestData userData) {

        UserRegistrationResponseData.ResponseState state = this.userManagementUtil.registerNewUser(userData);

        //TODO: activation emails?
//        if (state == UserRegistrationResponseData.ResponseState.CREATED) {
//            this.userManagementUtil.sendActivationEmail(locale, userData.getLogin());
//        }

        return this.responseCreator.createRegistrationResponse(locale, state);
    }

    @GetMapping("/resend-activation")
    public ResponseEntity<ResendActivationResponseData> resendActivation(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
                                                                         @RequestParam String login) {

        ResendActivationResponseData.ResponseState state = this.userManagementUtil.resendActivation(locale, login);

        return this.responseCreator.createResendActivationResponse(locale, state);
    }

    @GetMapping("/activate")
    public ResponseEntity<UserActivationResponseData> activate(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
                                                               @RequestParam String login, @RequestParam("a_str") String activationString) {

        UserActivationResponseData.ResponseState state = this.userManagementUtil.performUserActivation(login, activationString);

        return this.responseCreator.createActivationResponse(locale, state);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/change-password")
    public ResponseEntity<PasswordChangeResponseData> changePassword(@RequestHeader(value = "Accept-language", defaultValue = "en") Locale locale,
                                                                     @Valid @RequestBody PasswordChangeRequestData passwordData) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PasswordChangeResponseData.ResponseState state = this.userManagementUtil.changeUserPassword(login, passwordData.getOldPassword(),
                passwordData.getNewPassword());

        return this.responseCreator.createPasswordChangeResponse(locale, state);
    }
}
