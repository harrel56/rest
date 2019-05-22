package server.rest.usermanagement.register;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRegistrationResponseData implements Serializable {

    public enum ResponseState {
        CREATED(null), LOGIN_ALREADY_TAKEN("login"), EMAIL_ALREADY_TAKEN("email"), LOGIN_INVALID("login"), EMAIL_INVALID("email"), PASSWORD_INVALID("password"),
        UNKNOWN_ERROR(null);

        String field;

        ResponseState(String field) {
            this.field = field;
        }
    }

    private final ResponseState state;
    private final String message;
    private final String field;

    public UserRegistrationResponseData(ResponseState state, String message) {
        this.state = state;
        this.message = message;
        this.field = state.field;
    }

    public ResponseState getState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }

    public String getField() {
        return this.field;
    }
}
