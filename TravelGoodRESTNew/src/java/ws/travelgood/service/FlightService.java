/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.booking.FlightBooking;

/**
 *
 * @author mkucharek
 */
public interface FlightService extends BookingService<FlightBooking> {

    public List<FlightBooking> getAvailableBookings(Date date, String cityFrom, String cityTwo);

}
