/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager.impl;

import ws.travelgood.manager.ItineraryManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public class TravelGoodManager implements ItineraryManager {

    protected List<Itinerary> itineraryList;
    protected int nextId;
    // internal usage only
    private final int ITINERARY_NOT_FOUND = -1;

    private TravelGoodManager() {
        this.itineraryList = new ArrayList<Itinerary>();
        this.nextId = 1;

        // initial data - to be removed
        createItinerary(new Itinerary("u1"));
        createItinerary(new Itinerary("u1"));
        createItinerary(new Itinerary("u2", 3, ItineraryStatus.BOOKED));
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class TravelGoodManagerHolder {

        public static final TravelGoodManager INSTANCE = new TravelGoodManager();
    }

    public static TravelGoodManager getInstance() {
        return TravelGoodManagerHolder.INSTANCE;
    }

    public List<Itinerary> getAllItineraries() {
        return Collections.unmodifiableList(this.itineraryList);

    }

    public Itinerary getItinerary(Integer id) {

        for (Itinerary it : this.itineraryList) {
            if (it.getId().equals(id)) {
                return new Itinerary(it);
            }
        }

        return null;

    }

    public List<Itinerary> getUserItineraries(String userId) {

        List<Itinerary> retList = new ArrayList<Itinerary>();
        for (Itinerary it : this.itineraryList) {
            if (it.getUserId().equals(userId)) {
                retList.add(it);
            }
        }

        return retList;

    }

    public Itinerary createItinerary(Itinerary itinerary) {
        return this.createItinerary(itinerary, nextId++);

    }

    public boolean addHotel(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        if (it.getHotelBooking(hotelBookingNumber) != null) {
            // hotel already added
            return false;
        }

        it.addHotel(hotelBookingNumber);

        return this.updateItinerary(it);

    }

    public boolean deleteHotel(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        it.deleteHotel(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public boolean addFlight(Integer itineraryId, String hotelBookingNumber) {
        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        if (it.getFlightBooking(hotelBookingNumber) != null) {
            // flight already added
            return false;
        }

        it.addFlight(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public boolean deleteFlight(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        it.deleteFlight(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public boolean bookItinerary(Integer itineraryId) {

        Itinerary it = getItinerary(itineraryId);

        // check if itinerary is in PLANNING phase
        if (it.getCurrentStatus() != ItineraryStatus.PLANNING) {
            // cannot book as it is not in planning phase
            return false;

        }

        // book every single hotel
        for (HotelBooking b : it.getHotelBookingList()) {
        }

        // book every single hotel

        // book every single flight

        // in case of failure - cancel all the previous bookings, return to PLANNING phase

        return false;
    }

    public boolean cancelItinerary(Integer itineraryId) {

        // cancel all hotels

        // cancel all flights

        // on error - continue, but notify the user

        return false;
    }

    protected boolean updateItinerary(Itinerary it) {

        if (!it.getCurrentStatus().equals(ItineraryStatus.PLANNING)) {
            // cannot update itinerary
            return false;
        }

        int index = this.getItineraryIndex(it);

        if (index != ITINERARY_NOT_FOUND) {

            this.itineraryList.set(index, it);
            return true;

        } else {
            // item must have been deleted - cannot update
            return false;

        }
    }

    protected boolean deleteItinerary(Integer id) {
        return this.deleteItinerary(this.getItinerary(id));

    }

    protected boolean deleteItinerary(Itinerary itinerary) {
        if (itinerary.getId() == null || itinerary.getId().compareTo(nextId) >=
                0) {
            // invalid itinerary passed

            return false;

        } else {
            // delete

            int index = this.getItineraryIndex(itinerary);

            if (index != ITINERARY_NOT_FOUND) {

                this.itineraryList.remove(index);
                return true;



            } else {
                // item must have been deleted - cannot delete
                return false;
            }

        }
    }

    protected Itinerary createItinerary(Itinerary it, int id) {
        it.setId(Integer.valueOf(id));
        this.itineraryList.add(it);

        return it;

    }

    protected int getItineraryIndex(Itinerary it) {

        for (int i = 0; i < this.itineraryList.size(); ++i) {
            if (this.itineraryList.get(i).getId().equals(it.getId())) {
                return i;

            }
        }

        // not found
        return ITINERARY_NOT_FOUND;

    }
}
