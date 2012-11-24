/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import java.util.Date;
import java.util.List;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.flight.FlightList;
import ws.travelgood.types.hotel.HotelList;

/**
 *
 * @author mkucharek
 */
public interface ItineraryManager {

    public List<Itinerary> getAllItineraries();

    public Itinerary getItinerary(Integer id);

    public List<Itinerary> getUserItineraries(String userId);

    public Itinerary createItinerary(Itinerary itinerary);

    public HotelList getHotels(Date dateFrom, Date dateTo, String cityName);

    public boolean addHotel(Integer itineraryId, String hotelBookingNumber);

    public boolean deleteHotel(Integer itineraryId, String hotelBookingNumber);

    public FlightList getFlights(Date date, String cityFromName, String cityToName);

    public boolean addFlight(Integer itineraryId, String hotelBookingNumber);

    public boolean deleteFlight(Integer itineraryId, String hotelBookingNumber);

    public boolean bookItinerary(Integer itineraryId);

    public boolean cancelItinerary(Integer itineraryId);
    
}
