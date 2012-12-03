/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.booking.AbstractBooking;

/**
 *
 * @author mkucharek
 */
public interface AbstractBookingManager<T extends AbstractBooking> {

    public boolean book(T booking, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancel(T booking, CreditCardInfo ccInfo) throws BookingException;

}
