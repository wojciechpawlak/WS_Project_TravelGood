/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.statuses;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.booking.FlightBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class FlightsStatusRepresentation extends AbstractStatusRepresentation{

    private List<FlightBooking> bookingList;

    public FlightsStatusRepresentation() {
    }

    public FlightsStatusRepresentation(List<FlightBooking> bookingList) {
        this.bookingList = bookingList;
    }

    @XmlElementWrapper(name="flights")
    @XmlElement(name="flight")
    public List<FlightBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<FlightBooking> bookingList) {
        this.bookingList = bookingList;
    }
}
