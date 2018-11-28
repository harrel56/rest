package web.rest.tools.conversion;

/**
 * This exception is thrown on request's incorrect data. Should be handled by
 * exception handler and returned as bad request.
 * 
 * @author Harrel
 *
 */
@SuppressWarnings("serial")
public class ExpandableException extends RuntimeException {

	public ExpandableException() {
		this("Expand parameters invalid!");
	}

	public ExpandableException(String message) {
		this(message, null);
	}

	public ExpandableException(String message, Throwable cause) {
		super(message, cause);
	}
}
