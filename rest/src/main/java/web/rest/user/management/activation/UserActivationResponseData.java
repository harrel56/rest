package web.rest.user.management.activation;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserActivationResponseData implements Serializable {

	public static enum ResponseState {
		ACTIVATED, FAILED, UNKNOWN_ERROR
	}

	private final ResponseState state;
	private final String message;

	public UserActivationResponseData(ResponseState state, String message) {
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
