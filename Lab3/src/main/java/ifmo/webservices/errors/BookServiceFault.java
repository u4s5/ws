package ifmo.webservices.errors;

public class BookServiceFault {
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static BookServiceFault defaultInstance() {
        BookServiceFault fault = new BookServiceFault();
        return fault;
    }
}