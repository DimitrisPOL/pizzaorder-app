package gr.aegean.order.exception;

import javax.xml.ws.WebFault;

import gr.aegean.order.domain.ErrorItem;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Thrown when the service is currently over-loaded and cannot handle the request.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class ServiceUnavailableException extends WebApplicationException {
	public ServiceUnavailableException(String message){
		super(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new ErrorItem(Response.Status.SERVICE_UNAVAILABLE.getStatusCode(), "service_unavailable", message)).type(MediaType.APPLICATION_XML).build());
	}
	
	public ServiceUnavailableException(){
		super();
	}
}
