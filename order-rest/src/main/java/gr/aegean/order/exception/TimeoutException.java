package gr.aegean.order.exception;

import javax.xml.ws.WebFault;

/**
 * Thrown when the backend MySQL DBMS was not able to fulfill the user request based on the timeout provided.
 *
 * @author Kyriakos Kritikos
 */
@WebFault
public class TimeoutException extends RuntimeException {
}
