package server.rest.tools.conversion;

@SuppressWarnings("serial")
public class ConversionAnnotationException extends RuntimeException {

    public ConversionAnnotationException() {
        this("Wrongly annotated (@Conversion) methods in class ConversionUtil!");
    }

    public ConversionAnnotationException(String message) {
        this(message, null);
    }

    public ConversionAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
