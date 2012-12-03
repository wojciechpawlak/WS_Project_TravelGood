/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
public interface HotelManager {

    public List<HotelBooking> getHotels(Date dateFrom, Date dateTo, String cityName);

    public boolean bookHotel(String bookingNumber, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancelHotel(String bookingNumber) throws BookingException;

}
