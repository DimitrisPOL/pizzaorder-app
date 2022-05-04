package gr.aegean.order.exception;

import javax.xml.ws.WebFault;

/**
 * Thrown when a resource requested is not found.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class NotFoundException extends Exception {
}
