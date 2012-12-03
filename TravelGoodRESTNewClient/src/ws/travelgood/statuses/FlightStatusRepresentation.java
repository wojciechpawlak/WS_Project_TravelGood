/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.booking.FlightBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class FlightStatusRepresentation extends AbstractStatusRepresentation{

    private FlightBooking booking;

    public FlightStatusRepresentation() {
    }

    public FlightStatusRepresentation(FlightBooking booking) {
        this.booking = booking;
    }

    @XmlElement(name="flight")
    public FlightBooking getBooking() {
        return booking;
    }

    public void setBooking(FlightBooking booking) {
        this.booking = booking;
    }
}
