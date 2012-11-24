/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import java.util.List;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public interface ItineraryDAO {

    public List<Itinerary> getAllItineraries();

    public Itinerary getItinerary(Integer id);

    public List<Itinerary> getUserItineraries(String userId);

    public Itinerary createItinerary(Itinerary itinerary);

    public boolean addHotel(Integer itineraryId, String hotelBookingNumber);

    public boolean deleteHotel(Integer itineraryId, String hotelBookingNumber);

    public boolean bookItinerary(Integer itineraryId);

    public boolean cancelItinerary(Integer itineraryId);
    
}
