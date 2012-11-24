/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.flight;

import ws.travelgood.types.hotel.*;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.types.Booking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class FlightBooking extends Booking {

    private static final String TYPE = "FLIGHT";

    private FlightBooking() {
        super(TYPE, "");
        
    }
    public FlightBooking(String bookingNumber) {
        super(TYPE, bookingNumber);

    }
}
