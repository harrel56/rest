package server.rest.resources;

@SuppressWarnings("serial")
public class ImmutableDataModificationException extends RuntimeException {

    public ImmutableDataModificationException() {
        this("Immutable data modification detected!");
    }

    public ImmutableDataModificationException(String message) {
        this(message, null);
    }

    public ImmutableDataModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
