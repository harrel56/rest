package server.rest.usermanagement.register;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRegistrationRequestData implements Serializable {

    @NotNull
    private final String login;
    @NotNull
    private final String password;
    @NotNull
    private final String email;

    public UserRegistrationRequestData(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }
}
