/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service.impl;

import java.util.ArrayList;
import java.util.List;
import ws.travelgood.dao.ItineraryDAO;
import ws.travelgood.dao.TravelGoodDAO;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.banking.ExpirationDate;
import ws.travelgood.domain.booking.AbstractBooking;
import ws.travelgood.domain.booking.BookingStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;
import ws.travelgood.manager.BookingException;
import ws.travelgood.manager.LameDuckManager;
import ws.travelgood.manager.NiceViewManager;
import ws.travelgood.service.InvalidStatusException;
import ws.travelgood.service.ItineraryService;

/**
 *
 * @author mkucharek
 */
public class ItineraryServiceImpl implements ItineraryService {

    private ItineraryDAO itineraryDao;

    private LameDuckManager lameDuckManager;
    private NiceViewManager niceViewManager;

    private ItineraryServiceImpl() {
        
        itineraryDao = TravelGoodDAO.getInstance();

        lameDuckManager = new LameDuckManager();
        niceViewManager = new NiceViewManager();
    }

    /**
     * SingletonHolder is loaded on the first execution of getInstance()
     * or the first access to Holder.INSTANCE, not before.
     */
    private static class SingletonHolder {

        public static final ItineraryService INSTANCE = new ItineraryServiceImpl();
    }

    public static ItineraryService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Itinerary> getUserItineraries(String cid) {
        
        List<Itinerary> itList = itineraryDao.getItineraries(cid);

        if (ExpirationUtils.ENABLED) {

            boolean changed = false;

            for (Itinerary it : itList) {
                if (ExpirationUtils.isExpired(it)) {
                    itineraryDao.deleteItinerary(cid, it.getId());
                    changed = true;
                }
            }
            
            if (changed) {
                return itineraryDao.getItineraries(cid);
            } else {
                return itList;
            }

        } else {
            return itList;
        }
    }

    public Itinerary getItinerary(String cid, String iid) {
        Itinerary it = itineraryDao.getItinerary(cid, iid);

        if (ExpirationUtils.isExpired(it)) {
            itineraryDao.deleteItinerary(cid, iid);
            return null;

        }

        return it;
    }

    public Itinerary createItinerary(String cid, final Itinerary it) {

        Itinerary itNew = new Itinerary(it);

        // setting the status to planning
        itNew.setCurrentStatus(ItineraryStatus.PLANNING);

        if (!itineraryDao.putItinerary(cid, itNew)) {
            return null;

        }

        return itNew;
    }

    public boolean deleteItinerary(String cid, String iid) throws InvalidStatusException {

        Itinerary it = itineraryDao.getItinerary(cid, iid);

        if (it != null && ItineraryStatus.PLANNING.equals(it.getCurrentStatus())) {
            if (!itineraryDao.deleteItinerary(cid, iid)) {
                throw new InvalidStatusException();

            }

            return true;
        }

        return false;

    }

    public boolean bookItinerary(String cid, String iid,
            CreditCardInfo ccInfo) throws BookingException {

        Itinerary it = getItinerary(cid, iid);

        // check if itinerary is in PLANNING phase
        if (!ItineraryStatus.PLANNING.equals(it.getCurrentStatus())) {
            // cannot book as it is not in planning phase
            return false;

        }

        try {
            // book every single hotel (if not already booked)
            for (HotelBooking hb : it.getHotelBookingList()) {
                if (!BookingStatus.CONFIRMED.equals(hb.getBookingStatus())) {

                    this.niceViewManager.book(hb, ccInfo);
                    hb.setBookingStatus(BookingStatus.CONFIRMED);

                }
            }

            // book every single flight (if not already booked)
            for (FlightBooking fb : it.getFlightBookingList()) {
                if (!BookingStatus.CONFIRMED.equals(fb.getBookingStatus())) {

                    this.lameDuckManager.book(fb, ccInfo);
                    fb.setBookingStatus(BookingStatus.CONFIRMED);

                }
            }

        } catch (BookingException e) {
            // booking failure
            // cancel all previous bookings
            // delete

            List<AbstractBooking> failedBookingCancels = cancelAllBookings(it, ccInfo);

            // determining itinerary status
            it.setCurrentStatus(determineItineraryStatus(it));

            itineraryDao.putItinerary(cid, it);

            if (failedBookingCancels.size() > 0) {

                throw new BookingException("Itinerary booking failed , but the following bookings could not have been rolled back: " +
                        failedBookingCancels);
            }

            // keep status as PLANNING

            throw new BookingException("Itinerary booking failed, reverted to PLANNING status.");

        }

        // determining itinerary status
        it.setCurrentStatus(determineItineraryStatus(it));

        itineraryDao.putItinerary(cid, it);
        
        return true;

    }

    public boolean cancelItinerary(String cid, String iid, CreditCardInfo ccInfo) throws BookingException {

        Itinerary it = this.getItinerary(cid, iid);

        // check if itinerary is in BOOKED phase
        if (!ItineraryStatus.BOOKED.equals(it.getCurrentStatus())) {
            // cannot cancel as it is not in booked phase
            return false;

        }

        if (!ExpirationUtils.canBeCancelled(it)) {
            // remove
            itineraryDao.deleteItinerary(cid, iid);

            throw new NullPointerException();
        }

        List<AbstractBooking> failedBookingCancels = cancelAllBookings(it, ccInfo);

        if (failedBookingCancels.size() > 0) {

            itineraryDao.putItinerary(cid, it);
            
            throw new BookingException("Itinerary cancel failed for the following bookings: " +
                    failedBookingCancels);
        }

        // if all cancels are ok - delete itinerary
        itineraryDao.deleteItinerary(cid, iid);

        return true;
    }

    private ItineraryStatus determineItineraryStatus(Itinerary it) {

        for (AbstractBooking b : it.getHotelBookingList()) {
            if (BookingStatus.CONFIRMED.equals(b.getBookingStatus())) {
                return ItineraryStatus.BOOKED;
            }
        }
        
        for (AbstractBooking b : it.getFlightBookingList()) {
            if (BookingStatus.CONFIRMED.equals(b.getBookingStatus())) {
                return ItineraryStatus.BOOKED;
            }
        }

        return ItineraryStatus.PLANNING;

    }

    private List<AbstractBooking> cancelAllBookings(Itinerary it, CreditCardInfo ccInfo) {

        List<AbstractBooking> failedBookingCancels = new ArrayList<AbstractBooking>();

        // cancel all hotels
        for (HotelBooking hb : it.getHotelBookingList()) {

            if (BookingStatus.CONFIRMED.equals(hb.getBookingStatus())) {
                try {
                    
                    niceViewManager.cancel(hb, ccInfo);
                    hb.setBookingStatus(BookingStatus.CANCELLED);

                } catch (BookingException e) {
                    failedBookingCancels.add(hb);
                }

            }
        }

        // cancel all flights
        for (FlightBooking fb : it.getFlightBookingList()) {

            if (BookingStatus.CONFIRMED.equals(fb.getBookingStatus())) {
                try {

                    lameDuckManager.cancel(fb, ccInfo);
                    fb.setBookingStatus(BookingStatus.CANCELLED);

                } catch (BookingException e) {
                    failedBookingCancels.add(fb);

                }

            }
        }

        return failedBookingCancels;

    }

    

}
