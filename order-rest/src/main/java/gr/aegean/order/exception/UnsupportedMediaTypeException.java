package gr.aegean.order.exception;

import javax.xml.ws.WebFault;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Thrown when improper queries, update statements are issued to the Book Service.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class UnsupportedMediaTypeException extends RuntimeException {
}
