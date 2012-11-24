/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class Itinerary {

    private Integer id;
    private String userId;
    private ItineraryStatus currentStatus;

    private List<HotelBooking> hotelBookingList;
    private List<FlightBooking> flightBookingList;

    private Itinerary() {
    }

    public Itinerary(String userId) {
        this(userId, null, ItineraryStatus.PLANNING);

    }

    public Itinerary(String userId, Integer itineraryId, ItineraryStatus status) {
        this.id = itineraryId;
        this.userId = userId;
        this.currentStatus = status;
        this.hotelBookingList = new ArrayList<HotelBooking>();
        this.flightBookingList = new ArrayList<FlightBooking>();

    }

    public Itinerary(Itinerary it) {
        this.id = it.id;
        this.userId = it.userId;
        this.currentStatus = it.currentStatus;
        this.hotelBookingList = new ArrayList<HotelBooking>(it.hotelBookingList);
        this.flightBookingList = new ArrayList<FlightBooking>(it.flightBookingList);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the currentStatus
     */
    public ItineraryStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @param currentStatus the currentStatus to set
     */
    public void setCurrentStatus(ItineraryStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * @return the hotelBookingList
     */
    @XmlElementWrapper(name = "hotels")
    @XmlElement(name = "hotel")
    public List<HotelBooking> getHotelBookingList() {
        return Collections.unmodifiableList(hotelBookingList);
    }

    /**
     * @return the flightBookingList
     */
    @XmlElementWrapper(name = "flights")
    @XmlElement(name = "flight")
    public List<FlightBooking> getFlightBookingList() {
        return Collections.unmodifiableList(flightBookingList);
    }

    public HotelBooking getHotelBooking(String bookingNumber) {

        for (HotelBooking hb : this.hotelBookingList) {
            if (hb.getBookingNumber().equals(bookingNumber)) {
                return hb;
            }
        }

        return null;
    }

    public FlightBooking getFlightBooking(String bookingNumber) {

        for (FlightBooking hb : this.flightBookingList) {
            if (hb.getBookingNumber().equals(bookingNumber)) {
                return hb;
            }
        }

        return null;
    }

    public void addHotel(String bookingNumber) {
        this.hotelBookingList.add(new HotelBooking(bookingNumber));
    }

    public boolean deleteHotel(String bookingNumber) {

        HotelBooking toRemove = null;
        for (HotelBooking hb : this.getHotelBookingList()) {
            if (hb.getBookingNumber().equals(bookingNumber)) {
                toRemove = hb;
                break;

            }
        }

        if (toRemove != null) {
            this.hotelBookingList.remove(toRemove);

            return true;
        }

        return false;

    }

    public void addFlight(String bookingNumber) {
        this.flightBookingList.add(new FlightBooking(bookingNumber));
    }

    public boolean deleteFlight(String bookingNumber) {

        FlightBooking toRemove = null;
        for (FlightBooking hb : this.getFlightBookingList()) {
            if (hb.getBookingNumber().equals(bookingNumber)) {
                toRemove = hb;
                break;

            }
        }

        if (toRemove != null) {
            this.flightBookingList.remove(toRemove);

            return true;
        }

        return false;

    }

    @Override
    public String toString() {
        return "Itineary [" +
                "id = " + this.id + "; " +
                "userId = " + this.userId + "; " +
                "status = " + this.currentStatus + "; " +
                "hotelList = " + this.getHotelBookingList() + "; " +
                "flightList = " + this.getFlightBookingList() + "]";
    }
}
