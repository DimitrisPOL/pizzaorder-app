package gr.aegean.order.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Map the {@link UnsupportedSyntaxException} to the HTTP 400 code.
 * 
 * @author Kyriakos Kritikos
 */
@Provider
public class UnsupportedSyntaxMapper implements
		ExceptionMapper<UnsupportedSyntaxException> {

	public static final int CODE = 400;

	public static final String DESCRIPTION = "The request cannot be fulfilled due to bad syntax";

	public Response toResponse(UnsupportedSyntaxException ex) {
		return Response.status(CODE).entity(ex.getMessage()).type("text/plain").build();
	}
}
