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
import ws.travelgood.domain.booking.HotelBooking;
import ws.travelgood.manager.NiceViewManager;
import ws.travelgood.service.HotelService;

/**
 *
 * @author mkucharek
 */
public class HotelBookingServiceImpl extends AbstractBookingService<HotelBooking> implements HotelService {

    private NiceViewManager niceViewManager;
    
    private HotelBookingServiceImpl() {
        super(TravelGoodDAO.getInstance());

        niceViewManager = new NiceViewManager();

    }

    /**
     * SingletonHolder is loaded on the first execution of getInstance()
     * or the first access to Holder.INSTANCE, not before.
     */
    private static class SingletonHolder {

        public static final HotelService INSTANCE = new HotelBookingServiceImpl();
    }

    public static HotelService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HotelBooking getBooking(String cid, String iid,
            String bid) {
        
        return itineraryDao.getItinerary(cid, iid).getHotelBooking(bid);
    }

    public HotelBooking addBooking(String cid, String iid,
            final HotelBooking hb) {

        Itinerary it = itineraryDao.getItinerary(cid, iid);

        if (it == null) {
            return null;
        }

        if (ItineraryStatus.PLANNING.equals(it.getCurrentStatus())) {
            
            HotelBooking hbNew = new HotelBooking(hb);

            // setting status to unconfirmed
            hb.setBookingStatus(BookingStatus.UNCONFIRMED);

            if (!itineraryDao.addBooking(cid, iid, hbNew)) {
                return null;
            }

            return hbNew;

        } else {
            return null;

        }

    }

    public List<HotelBooking> getAvailableBookings(Date dateFrom, Date dateTo,
            String city) {

        return niceViewManager.getHotels(dateFrom, dateTo, city);

    }

}
