/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class Itinerary extends AbstractEntity {

    private String id;

    private ItineraryStatus currentStatus;

    private List<HotelBooking> hotelBookingList;
    private List<FlightBooking> flightBookingList;

    public Itinerary() {
        this(null, ItineraryStatus.PLANNING, new ArrayList<HotelBooking>(), new ArrayList<FlightBooking>());
    }

    public Itinerary(String id, ItineraryStatus status) {
        this(id, status, new ArrayList<HotelBooking>(), new ArrayList<FlightBooking>());

    }

    public Itinerary(String id, ItineraryStatus currentStatus,
            List<HotelBooking> hotelBookingList,
            List<FlightBooking> flightBookingList) {
        this.id = id;
        this.currentStatus = currentStatus;
        this.hotelBookingList = hotelBookingList;
        this.flightBookingList = flightBookingList;
    }

    public Itinerary(Itinerary it) {
        this(it.getId(),
                it.currentStatus,
                new ArrayList<HotelBooking>(),
                new ArrayList<FlightBooking>());

        for (HotelBooking hb : it.hotelBookingList) {
            this.hotelBookingList.add(new HotelBooking(hb));
        }

        for (FlightBooking fb : it.flightBookingList) {
            this.flightBookingList.add(new FlightBooking(fb));
        }
    }

    @Override
    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the currentStatus
     */
    @XmlElement
    public ItineraryStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @param currentStatus the currentStatus to set
     */
    public void setCurrentStatus(ItineraryStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @XmlElementWrapper(name="flights")
    @XmlElement(name="flight")
    public List<FlightBooking> getFlightBookingList() {
        return flightBookingList;
    }

    public void setFlightBookingList(List<FlightBooking> flightBookingList) {
        this.flightBookingList = flightBookingList;
    }

    @XmlElementWrapper(name="hotels")
    @XmlElement(name="hotel")
    public List<HotelBooking> getHotelBookingList() {
        return hotelBookingList;
    }

    public void setHotelBookingList(List<HotelBooking> hotelBookingList) {
        this.hotelBookingList = hotelBookingList;
    }


    public HotelBooking getHotelBooking(String id) {
        for (HotelBooking hb : hotelBookingList) {
            if (hb.getId().equals(id)) {
                return hb;
            }
        }

        return null;
    }

    public FlightBooking getFlightBooking(String id) {
        for (FlightBooking hb : flightBookingList) {
            if (hb.getId().equals(id)) {
                return hb;
            }
        }

        return null;
    }

    public void addHotel(HotelBooking hbe) {

        deleteHotel(hbe.getId());

        hotelBookingList.add(hbe);

    }

    public boolean deleteHotel(String id) {
        Iterator<HotelBooking> i = hotelBookingList.iterator();

        while (i.hasNext()) {
            HotelBooking ihb = i.next();

            if (ihb.getId().equals(id)) {
                i.remove();
                return true;
            }
        }

        return false;

    }

    public void addFlight(FlightBooking fbe) {

        deleteFlight(fbe.getId());

        flightBookingList.add(fbe);

    }

    public boolean deleteFlight(String id) {

        Iterator<FlightBooking> i = flightBookingList.iterator();

        while (i.hasNext()) {
            FlightBooking ihb = i.next();

            if (ihb.getId().equals(id)) {
                i.remove();
                return true;
            }
        }

        return false;

    }

    @Override
    public String toString() {
        return "ItineraryEntity [" +
                "id = " + this.getId() + "; " +
                "status = " + this.getCurrentStatus() + "; " +
                "hotelBookingList = " + this.hotelBookingList + "; " +
                "flightBookingList = " + this.flightBookingList +"]";
    }
    
}
