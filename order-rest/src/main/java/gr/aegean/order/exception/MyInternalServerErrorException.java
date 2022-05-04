package gr.aegean.order.exception;

import javax.xml.ws.WebFault;

import gr.aegean.order.domain.ErrorItem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Thrown when the processing of the request resulted in an unexpected failure.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class MyInternalServerErrorException extends WebApplicationException {
	public MyInternalServerErrorException(String message){
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorItem(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "internal server error", message)).type(MediaType.APPLICATION_XML).build());
	}
	
	public MyInternalServerErrorException(){
		super();
	}
}
