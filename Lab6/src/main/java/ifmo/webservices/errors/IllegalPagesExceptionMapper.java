package ifmo.webservices.errors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalPagesExceptionMapper implements ExceptionMapper<IllegalPagesException> {
    @Override
    public Response toResponse(IllegalPagesException e) {
        return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}