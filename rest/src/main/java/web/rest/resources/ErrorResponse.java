package web.rest.resources;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class ErrorResponse implements Serializable {

	private final HttpStatus status;
	private final String message;

	public ErrorResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public String getMessage() {
		return this.message;
	}

}
