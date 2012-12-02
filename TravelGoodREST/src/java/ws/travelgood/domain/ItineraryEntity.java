/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.domain;

import ws.travelgood.states.ItineraryStatus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mkucharek
 */
public class ItineraryEntity extends AbstractEntity {

    private String userId;
    private ItineraryStatus currentStatus;

    private List<HotelBookingEntity> hotelBookingList;
    private List<FlightBookingEntity> flightBookingList;

    public ItineraryEntity(String userId, Integer itineraryId, ItineraryStatus status) {
        super(itineraryId);
        this.userId = userId;
        this.currentStatus = status;
        this.hotelBookingList = new ArrayList<HotelBookingEntity>();
        this.flightBookingList = new ArrayList<FlightBookingEntity>();

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
    public List<HotelBookingEntity> getHotelBookingList() {
        return hotelBookingList;
    }

    /**
     * @param hotelBookingList the hotelBookingList to set
     */
    public void setHotelBookingList(List<HotelBookingEntity> hotelBookingList) {
        this.hotelBookingList = hotelBookingList;
    }

    /**
     * @return the flightBookingList
     */
    public List<FlightBookingEntity> getFlightBookingList() {
        return flightBookingList;
    }

    /**
     * @param flightBookingList the flightBookingList to set
     */
    public void setFlightBookingList(List<FlightBookingEntity> flightBookingList) {
        this.flightBookingList = flightBookingList;
    }

    public HotelBookingEntity getHotelBooking(Integer bookingId) {

        for (HotelBookingEntity hbe : this.hotelBookingList) {
            if (hbe.getId().equals(bookingId)) {
                return hbe;
            }
        }

        return null;
    }

    public FlightBookingEntity getFlightBooking(Integer bookingId) {

        for (FlightBookingEntity fbe : this.flightBookingList) {
            if (fbe.getId().equals(bookingId)) {
                return fbe;
            }
        }

        return null;
    }

    public void addHotel(HotelBookingEntity hbe) {
        this.hotelBookingList.add(hbe);
    }

    public boolean deleteHotel(Integer hotelId) {

        Iterator<HotelBookingEntity> iterator = this.getHotelBookingList().iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(hotelId)) {
                iterator.remove();
                return true;

            }
        }

        return false;

    }

    public void addFlight(FlightBookingEntity fbe) {
        this.flightBookingList.add(fbe);
    }

    public boolean deleteFlight(Integer flightId) {

        Iterator<FlightBookingEntity> iterator = this.getFlightBookingList().iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(flightId)) {
                iterator.remove();
                return true;

            }
        }

        return false;

    }

    @Override
    public String toString() {
        return "ItineraryEntity [" +
                "id = " + this.getId() + "; " +
                "userId = " + this.getUserId() + "; " +
                "status = " + this.getCurrentStatus() + "; " +
                "hotelBookingList = " + this.hotelBookingList + "; " +
                "flightBookingList = " + this.flightBookingList +"]";
    }
    
}
