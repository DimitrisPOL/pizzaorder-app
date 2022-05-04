package gr.aegean.order.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebFault;

import gr.aegean.order.domain.ErrorItem;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Thrown when the operations of the Book Service are called in a wrong way.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class BadRequestException extends WebApplicationException {
	public BadRequestException(String message){
		super(Response.status(Response.Status.BAD_REQUEST).entity(new ErrorItem(Response.Status.BAD_REQUEST.getStatusCode(), "bad_parameter", message)).type(MediaType.APPLICATION_XML).build());
	}
	
	public BadRequestException(){
		super();
	}
}
