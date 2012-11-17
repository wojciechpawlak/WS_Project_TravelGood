/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ws.niceview.types.HotelType;

/**
 *
 * @author mkucharek
 */
@XmlType
@XmlRootElement
public class Itinerary {

    private String id;

    private String userId;

    private ItineraryStatus currentStatus;

    private List<HotelType> hotelList;

    private Itinerary() {

    }
    
    public Itinerary(String userId, String itineraryId) {
        this(userId, itineraryId, ItineraryStatus.PLANNING);

    }

    public Itinerary(String userId, String itineraryId, ItineraryStatus status) {
        this.id = itineraryId;
        this.userId = userId;
        this.currentStatus = status;
        this.hotelList = new ArrayList<HotelType>();

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
     * @return the hotelList
     */
    public List<HotelType> getHotelList() {
        return hotelList;
    }

    /**
     * @param hotelList the hotelList to set
     */
    public void setHotelList(List<HotelType> hotelList) {
        this.setHotelList(hotelList);
    }

    @Override
    public String toString() {
        return "Itineary [" +
                "id = " + this.id + "; " +
                "userId = " + this.userId + "; " +
                "status = " + this.currentStatus + "; " +
                "hotelList = " + this.hotelList + "]";
    }

}
