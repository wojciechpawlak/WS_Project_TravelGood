/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

/**
 *
 * @author mkucharek
 */
public interface BookingManager {

    public boolean book(String bookingNumber);

    public boolean cancel(String bookingNumber);

}
