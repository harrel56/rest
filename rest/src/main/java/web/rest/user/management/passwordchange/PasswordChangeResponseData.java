package web.rest.user.management.passwordchange;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PasswordChangeResponseData implements Serializable {

	public static enum ResponseState {
		CHANGED, PASSWORD_THE_SAME, PASSWORD_INVALID, NEW_PASSWORD_INVALID, UNKNOWN_ERROR
	}

	private final ResponseState state;
	private final String message;

	public PasswordChangeResponseData(ResponseState state, String message) {
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
