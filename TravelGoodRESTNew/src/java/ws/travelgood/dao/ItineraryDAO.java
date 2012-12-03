/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.dao;

import java.util.List;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.booking.AbstractBooking;

/**
 *
 * @author mkucharek
 */
public interface ItineraryDAO {

    public List<Itinerary> getItineraries(String cid);
    
    public Itinerary getItinerary(String cid, String iid);

    public boolean putItinerary(String cid, Itinerary it);

    public boolean deleteItinerary(String cid, String iid);

    public boolean addBooking(String cid, String iid, AbstractBooking booking);

    public boolean deleteBooking(String cid, String iid, AbstractBooking booking);

}
