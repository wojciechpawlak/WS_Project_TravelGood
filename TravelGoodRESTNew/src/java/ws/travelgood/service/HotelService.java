/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
public interface HotelService extends BookingService<HotelBooking> {

    public List<HotelBooking> getAvailableBookings(Date dateFrom, Date dateTo, String city);

}
