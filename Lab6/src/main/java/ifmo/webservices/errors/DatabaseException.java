package ifmo.webservices.errors;

public class DatabaseException extends Exception {
    public static DatabaseException DEFAULT_INSTANCE = new
            DatabaseException("Error occurred during database operation");

    public DatabaseException(String message) {
        super(message);
    }
}