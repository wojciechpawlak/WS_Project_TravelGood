/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import ws.travelgood.types.banking.CreditCardInfo;

/**
 *
 * @author mkucharek
 */
public interface BookingManager {

    public boolean book(String bookingNumber, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancel(String bookingNumber);

}
