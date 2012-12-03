/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.booking.FlightBooking;

/**
 *
 * @author mkucharek
 */
public interface FlightManager extends AbstractBookingManager<FlightBooking> {

    public List<FlightBooking> getFlights(Date date, String cityFromName,
            String cityToName);

}
