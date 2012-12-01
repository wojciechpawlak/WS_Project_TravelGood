/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import java.util.Date;
import java.util.List;
import ws.travelgood.domain.FlightBookingEntity;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightList;

/**
 *
 * @author mkucharek
 */
public interface FlightManager {

    public List<FlightBookingEntity> getFlights(Date date, String cityFromName,
            String cityToName);

    public boolean bookFlight(String bookingNumber, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancelFlight(String bookingNumber, double price, CreditCardInfo ccInfo) throws BookingException;

}
