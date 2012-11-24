/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import java.util.List;
import ws.travelgood.types.Itinerary;

/**
 *
 * @author mkucharek
 */
public interface ItineraryManager {

    public List<Itinerary> getAllItineraries();

    public Itinerary getItinerary(Integer id);

    public List<Itinerary> getUserItineraries(String userId);

    public Itinerary createItinerary(Itinerary itinerary);

    public boolean addHotel(Integer itineraryId, String hotelBookingNumber);

    public boolean deleteHotel(Integer itineraryId, String hotelBookingNumber);

    public boolean addFlight(Integer itineraryId, String hotelBookingNumber);

    public boolean deleteFlight(Integer itineraryId, String hotelBookingNumber);
    
}
