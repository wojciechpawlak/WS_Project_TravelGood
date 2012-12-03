/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service.impl;

import java.util.Date;
import java.util.List;
import ws.travelgood.dao.TravelGoodDAO;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.booking.BookingStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.manager.LameDuckManager;
import ws.travelgood.service.FlightService;

/**
 *
 * @author mkucharek
 * @author tgherghescu
 */
public class FlightBookingServiceImpl extends AbstractBookingService<FlightBooking> implements FlightService {

    private LameDuckManager lameDuckManager;

    private FlightBookingServiceImpl() {
        super(TravelGoodDAO.getInstance());

        lameDuckManager = new LameDuckManager();

    }
    /**
     * SingletonHolder is loaded on the first execution of getInstance()
     * or the first access to Holder.INSTANCE, not before.
     */
    private static class SingletonHolder {

        public static final FlightService INSTANCE = new FlightBookingServiceImpl();
    }

    public static FlightService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FlightBooking getBooking(String cid, String iid,
            String bid) {

        return itineraryDao.getItinerary(cid, iid).getFlightBooking(bid);

    }

    public FlightBooking addBooking(String cid, String iid,
            FlightBooking fb) {

        Itinerary it = itineraryDao.getItinerary(cid, iid);

        if (it == null) {
            return null;
        }

        if (ItineraryStatus.PLANNING.equals(it.getCurrentStatus())) {

            FlightBooking fbNew = new FlightBooking(fb);

            // setting status to unconfirmed
            fb.setBookingStatus(BookingStatus.UNCONFIRMED);

            if (!itineraryDao.addBooking(cid, iid, fbNew)) {
                return null;
            }

            return fbNew;

        } else {
            return null;

        }
    }

    public List<FlightBooking> getAvailableBookings(Date date, String cityFrom,
            String cityTwo) {

        return lameDuckManager.getFlights(date, cityFrom, cityTwo);

    }

}
