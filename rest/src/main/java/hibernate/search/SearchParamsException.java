package hibernate.search;

@SuppressWarnings("serial")
public class SearchParamsException extends RuntimeException {

	public SearchParamsException() {
		super();
	}

	public SearchParamsException(String msg) {
		super(msg);
	}

	public SearchParamsException(String msg, Throwable t) {
		super(msg, t);
	}
}
