package ifmo.webservices.errors;

public class IllegalAuthorException extends Exception {
    public static IllegalAuthorException DEFAULT_INSTANCE = new
            IllegalAuthorException("Book author cannot be null or empty");

    public IllegalAuthorException(String message) {
        super(message);
    }
}