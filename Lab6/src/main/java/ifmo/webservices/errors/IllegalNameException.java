package ifmo.webservices.errors;

public class IllegalNameException extends Exception {
    public static IllegalNameException DEFAULT_INSTANCE = new
            IllegalNameException("personName cannot be null or empty");

    public IllegalNameException(String message) {
        super(message);
    }
}