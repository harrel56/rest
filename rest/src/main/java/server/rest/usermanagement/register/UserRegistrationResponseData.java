package server.rest.usermanagement.register;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRegistrationResponseData implements Serializable {

    public enum ResponseState {
        CREATED(true), LOGIN_ALREADY_TAKEN(false), EMAIL_ALREADY_TAKEN(false), LOGIN_INVALID(false), EMAIL_INVALID(false), PASSWORD_INVALID(false),
        UNKNOWN_ERROR(false);

        private boolean succeded;

        private ResponseState(boolean succeded) {
            this.succeded = succeded;
        }

        public boolean succeded() {
            return this.succeded;
        }
    }

    private final ResponseState state;
    private final String message;

    public UserRegistrationResponseData(ResponseState state, String message) {
        this.state = state;
        this.message = message;
    }

    public ResponseState getState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }
}
