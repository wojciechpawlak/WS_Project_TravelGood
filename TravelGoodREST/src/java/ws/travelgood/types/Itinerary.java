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

    }

    public Itinerary(Itinerary it) {
        this.id = it.id;
        this.userId = it.userId;
        this.currentStatus = it.currentStatus;
        this.hotelBookingList = new ArrayList<HotelBooking>(it.hotelBookingList);
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

    public HotelBooking getHotelBooking(String bookingNumber) {

        for (HotelBooking hb : this.hotelBookingList) {
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
            this.getHotelBookingList().remove(toRemove);

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
                "hotelList = " + this.getHotelBookingList() + "]";
    }
}
