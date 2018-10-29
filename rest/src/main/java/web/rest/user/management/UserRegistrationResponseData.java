package web.rest.user.management;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRegistrationResponseData implements Serializable {

	public static enum ResponseState {
		CREATED(true), LOGIN_ALREADY_TAKEN(false), EMAIL_ALREADY_TAKEN(false), INVALID_LOGIN(false),
		INVALID_EMAIL(false), INVALID_PASSWORD(false), UNKNOWN(false);

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
