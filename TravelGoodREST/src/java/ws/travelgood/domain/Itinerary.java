/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ws.travelgood.domain.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class Itinerary {

    private String id;

    private String userId;

    private ItineraryStatus currentStatus;

    private List<HotelBooking> hotelBookingList;

    private Itinerary() {

    }
    
    public Itinerary(String userId, String itineraryId) {
        this(userId, itineraryId, ItineraryStatus.PLANNING);

    }

    public Itinerary(String userId, String itineraryId, ItineraryStatus status) {
        this.id = itineraryId;
        this.userId = userId;
        this.currentStatus = status;
        this.hotelBookingList = new ArrayList<HotelBooking>();

    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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
    @XmlElementWrapper(name="hotels")
    @XmlElement(name="hotel")
    public List<HotelBooking> getHotelBookingList() {
        return hotelBookingList;
    }

    /**
     * @param hotelBookingList the hotelBookingList to set
     */
    public void setHotelBookingList(List<HotelBooking> hotelBookingList) {
        this.hotelBookingList = hotelBookingList;
    }

    

    @Override
    public String toString() {
        return "Itineary [" +
                "id = " + this.id + "; " +
                "userId = " + this.userId + "; " +
                "status = " + this.currentStatus + "; " +
                "hotelList = " + this.getHotelBookingList() + "]";
    }

}
