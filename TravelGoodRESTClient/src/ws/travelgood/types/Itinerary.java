/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.types;

import java.util.ArrayList;
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

    private String userId;
    private ItineraryStatus currentStatus;

    private List<HotelBooking> hotelBookingList;
    private List<FlightBooking> flightBookingList;

    private Itinerary() {
        this.hotelBookingList = new ArrayList<HotelBooking>();
        this.flightBookingList = new ArrayList<FlightBooking>();
        
    }

    public Itinerary(String userId) {
        this(userId, ItineraryStatus.PLANNING);

    }

    public Itinerary(String userId, ItineraryStatus status) {
        this(userId, status, new ArrayList<HotelBooking>(), new ArrayList<FlightBooking>());

    }

    public Itinerary(String userId, ItineraryStatus status, List<HotelBooking> hotelBookingList, List<FlightBooking> flightBookingList) {
        this.userId = userId;
        this.currentStatus = status;
        this.hotelBookingList = hotelBookingList;
        this.flightBookingList = flightBookingList;
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
        return hotelBookingList;
    }

    /**
     * @return the flightBookingList
     */
    @XmlElementWrapper(name = "flights")
    @XmlElement(name = "flight")
    public List<FlightBooking> getFlightBookingList() {
        return flightBookingList;
    }

    @Override
    public String toString() {
        return "Itineary [" +
                "userId = " + this.userId + "; " +
                "status = " + this.currentStatus + "; " +
                "hotelList = " + this.hotelBookingList + "; " +
                "flightList = " + this.flightBookingList + "]";
    }
    
}
