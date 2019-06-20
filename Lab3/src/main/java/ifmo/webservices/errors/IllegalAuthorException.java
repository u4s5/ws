package ifmo.webservices.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "ifmo.webservices.errors.BookServiceFault")
public class IllegalAuthorException extends Exception {
    private final BookServiceFault fault;

    public IllegalAuthorException(String message, BookServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public IllegalAuthorException(String message, BookServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public BookServiceFault getFaultInfo() {
        return fault;
    }
}