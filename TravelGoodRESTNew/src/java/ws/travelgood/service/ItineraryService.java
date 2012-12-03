/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import java.util.List;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.manager.BookingException;

/**
 *
 * @author mkucharek
 */
public interface ItineraryService {

    public List<Itinerary> getUserItineraries(String customerId);

    public Itinerary getItinerary(String customerId, String id);

    public Itinerary createItinerary(String customerId, final Itinerary it);

    public boolean deleteItinerary(String customerId, String id) throws InvalidStatusException;

    public boolean bookItinerary(String customerId, String itineraryId, final CreditCardInfo ccInfo) throws BookingException;

    public boolean cancelItinerary(String customerId, String itineraryId, final CreditCardInfo ccInfo) throws BookingException;

}
