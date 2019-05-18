package server.rest.tools.conversion;

/**
 * Exception thrown when data expansion configuration is faulty. The exception
 * is severe and the response should be marked with status 500.
 *
 * @author Harrel
 */
@SuppressWarnings("serial")
public class DataExpansionException extends RuntimeException {

    public DataExpansionException() {
        this("Data expansion failed!");
    }

    public DataExpansionException(String message) {
        this(message, null);
    }

    public DataExpansionException(String message, Throwable cause) {
        super(message, cause);
    }
}
