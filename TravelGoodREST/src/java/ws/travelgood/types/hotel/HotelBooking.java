/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.hotel;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.types.Booking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelBooking extends Booking {

    private static final String TYPE = "HOTEL";

    private HotelBooking() {
        super(TYPE, "");
        
    }
    public HotelBooking(String bookingNumber) {
        super(TYPE, bookingNumber);

    }
}
