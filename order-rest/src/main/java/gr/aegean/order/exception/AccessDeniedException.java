package gr.aegean.order.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.ws.WebFault;

import gr.aegean.order.domain.ErrorItem;

/**
 * Thrown when the operations of the Book Service are called in a wrong way.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class AccessDeniedException extends WebApplicationException {
	public AccessDeniedException(String message){
		super(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorItem(Response.Status.UNAUTHORIZED.getStatusCode(), "access_denied", message)).type(MediaType.APPLICATION_XML).build());
	}
	
	public AccessDeniedException(){
		super();
	}
}
