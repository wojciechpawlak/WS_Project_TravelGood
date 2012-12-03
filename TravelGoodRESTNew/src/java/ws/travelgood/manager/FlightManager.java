/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.banking.CreditCardInfo;

/**
 *
 * @author mkucharek
 */
public interface FlightManager {

    public List<FlightBooking> getFlights(Date date, String cityFromName,
            String cityToName);

    public boolean bookFlight(String bookingNumber, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancelFlight(String bookingNumber, double price, CreditCardInfo ccInfo) throws BookingException;

}
