/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.statuses;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelsStatusRepresentation extends AbstractStatusRepresentation{

    private List<HotelBooking> bookingList;

    public HotelsStatusRepresentation() {
    }

    public HotelsStatusRepresentation(List<HotelBooking> bookingList) {
        this.bookingList = bookingList;
    }

    @XmlElementWrapper(name="hotels")
    @XmlElement(name="hotel")
    public List<HotelBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<HotelBooking> bookingList) {
        this.bookingList = bookingList;
    }
}
