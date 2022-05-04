package gr.aegean.order.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link ServiceUnavailableException} to the HTTP 503 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class ServiceUnavailableMapper implements
		ExceptionMapper<ServiceUnavailableException> {

	public static final int CODE = 503;

	public static final String DESCRIPTION = "The service is currently unavailable";

	public Response toResponse(ServiceUnavailableException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
