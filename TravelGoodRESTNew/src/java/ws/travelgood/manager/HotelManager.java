/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
public interface HotelManager extends AbstractBookingManager<HotelBooking> {

    public List<HotelBooking> getHotels(Date dateFrom, Date dateTo, String cityName);

}
