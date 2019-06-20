package ifmo.webservices.errors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalAuthorExceptionMapper implements ExceptionMapper<IllegalAuthorException> {
    @Override
    public Response toResponse(IllegalAuthorException e) {
        return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}