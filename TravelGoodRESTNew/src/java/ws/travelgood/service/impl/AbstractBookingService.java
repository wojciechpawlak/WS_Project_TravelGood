/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service.impl;

import ws.travelgood.dao.ItineraryDAO;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.booking.AbstractBooking;
import ws.travelgood.service.BookingService;

/**
 *
 * @author mkucharek
 */
public abstract class AbstractBookingService<T extends AbstractBooking> implements BookingService<T> {

    protected ItineraryDAO itineraryDao;

    protected AbstractBookingService(ItineraryDAO itineraryDao) {
        this.itineraryDao = itineraryDao;
        
    }

    public boolean deleteBooking(String cid, String iid,
            String bid) {

        Itinerary it = itineraryDao.getItinerary(cid, iid);

        if (it == null) {
            return false;
        }

        if (ItineraryStatus.PLANNING.equals(it.getCurrentStatus())) {
            return itineraryDao.deleteBooking(cid, iid, getBooking(cid, iid, bid));

        } else {
            return false;

        }

    }
    
}
