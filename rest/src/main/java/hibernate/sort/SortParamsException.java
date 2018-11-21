package hibernate.sort;

@SuppressWarnings("serial")
public class SortParamsException extends RuntimeException {

	public SortParamsException() {
		super();
	}

	public SortParamsException(String msg) {
		super(msg);
	}

	public SortParamsException(String msg, Throwable t) {
		super(msg, t);
	}
}
