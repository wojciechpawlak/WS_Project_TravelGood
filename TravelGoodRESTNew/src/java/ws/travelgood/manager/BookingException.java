/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

/**
 *
 * @author mkucharek
 */
public class BookingException extends Exception {

    public BookingException(String message) {
        super(message);
    }

    public BookingException(String message, Throwable t) {
        super(message, t);
    }

}
