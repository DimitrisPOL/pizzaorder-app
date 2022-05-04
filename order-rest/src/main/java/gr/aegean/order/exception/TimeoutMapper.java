package gr.aegean.order.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link UnsupportedSyntaxException} to the HTTP 504 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class TimeoutMapper implements
		ExceptionMapper<TimeoutException> {

	public static final int CODE = 504;

	public static final String DESCRIPTION = "The request cannot be fulfilled as query evaluation/update time is bigger than timeout threshold";

	public Response toResponse(TimeoutException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
