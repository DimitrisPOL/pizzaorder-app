package gr.aegean.order.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link NotFoundException} to the HTTP 404 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class NotFoundMapper implements
		ExceptionMapper<NotFoundException> {

	public static final int CODE = 404;

	public static final String DESCRIPTION = "The requested resource could not be found";

	public Response toResponse(NotFoundException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
