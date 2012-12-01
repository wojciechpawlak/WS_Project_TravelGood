/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import java.util.Date;
import java.util.List;
import ws.travelgood.manager.BookingException;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public interface ItineraryService {

    public Integer createItinerary(Itinerary it);

    public List<Itinerary> getAllItineraries();

    public List<Itinerary> getUserItineraries(String userId);

    public Itinerary getItinerary(Integer id);

    public HotelBooking getHotelBooking(Integer itineraryId, Integer bookingId);

    public Integer addHotel(Integer itineraryId, HotelBooking booking);

    public boolean deleteHotel(Integer itineraryId, Integer hotelBookingId);

    public FlightBooking getFlightBooking(Integer itineraryId, Integer bookingId);

    public Integer addFlight(Integer itineraryId, FlightBooking booking);

    public boolean deleteFlight(Integer itineraryId, Integer flightBookingId);

    public boolean bookItinerary(Integer itineraryId, CreditCardInfo ccInfo) throws BookingException;

    public boolean cancelItinerary(Integer itineraryId, CreditCardInfo ccInfo) throws BookingException;

    public List<HotelBooking> getAvailableHotelBookings(Date dateFrom, Date dateTo, String city);

    public List<FlightBooking> getAvailableFlightBookings(Date date, String cityFrom, String cityTwo);

}
