package ifmo.webservices.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "ifmo.webservices.errors.BookServiceFault")
public class IllegalPagesException extends Exception {
    private final BookServiceFault fault;

    public IllegalPagesException(String message, BookServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public IllegalPagesException(String message, BookServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public BookServiceFault getFaultInfo() {
        return fault;
    }
}