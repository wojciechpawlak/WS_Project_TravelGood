/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import ws.travelgood.domain.booking.AbstractBooking;

/**
 *
 * @author mkucharek
 */
public interface BookingService<T extends AbstractBooking> {

    public T getBooking(String cid, String iid, String bid);

    public T addBooking(String cid, String iid, T booking);

    public boolean deleteBooking(String cid, String iid, String bid);

}
