/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelStatusRepresentation extends AbstractStatusRepresentation{

    private HotelBooking booking;

    public HotelStatusRepresentation() {
    }

    public HotelStatusRepresentation(HotelBooking booking) {
        this.booking = booking;
    }

    @XmlElement(name="hotel")
    public HotelBooking getBooking() {
        return booking;
    }

    public void setBooking(HotelBooking booking) {
        this.booking = booking;
    }
}
