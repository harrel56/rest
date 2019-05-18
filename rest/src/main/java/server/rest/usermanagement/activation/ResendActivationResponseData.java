package server.rest.usermanagement.activation;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResendActivationResponseData implements Serializable {

    public enum ResponseState {
        RESENT, USER_ALREADY_ACTIVATED, USER_INVALID, UNKNOWN_ERROR
    }

    private final ResponseState state;
    private final String message;

    public ResendActivationResponseData(ResponseState state, String message) {
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
