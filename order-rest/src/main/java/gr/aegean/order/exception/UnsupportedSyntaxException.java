package gr.aegean.order.exception;

import javax.xml.ws.WebFault;

/**
 * Thrown when improper queries are issued to the Book Service.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class UnsupportedSyntaxException extends RuntimeException {
}
