package gr.aegean.order.exception;

import javax.xml.ws.WebFault;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Thrown when requested media type is not acceptable.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class NotAcceptedException extends RuntimeException {
}
