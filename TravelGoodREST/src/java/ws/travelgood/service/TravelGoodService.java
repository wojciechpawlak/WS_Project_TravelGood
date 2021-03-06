/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import ws.travelgood.domain.AbstractEntity;
import ws.travelgood.domain.BookingEntity;
import ws.travelgood.domain.FlightBookingEntity;
import ws.travelgood.domain.HotelBookingEntity;
import ws.travelgood.manager.BookingException;
import ws.travelgood.manager.LameDuckManager;
import ws.travelgood.manager.NiceViewManager;
import ws.travelgood.domain.ItineraryEntity;
import ws.travelgood.service.assembler.AssemblerUtility;
import ws.travelgood.states.BookingStatus;
import ws.travelgood.types.Itinerary;
import ws.travelgood.states.ItineraryStatus;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public class TravelGoodService implements ItineraryService {

    private List<ItineraryEntity> itineraryList;

    private LameDuckManager lameDuckManager;
    private NiceViewManager niceViewManager;

    private TravelGoodService() {
        this.itineraryList = new ArrayList<ItineraryEntity>();

        createItinerary(new Itinerary("u1"));
        createItinerary(new Itinerary("u1"));
        this.itineraryList.add(new ItineraryEntity("u1", getNextId(
                itineraryList),
                ItineraryStatus.BOOKED));

        this.lameDuckManager = new LameDuckManager();
        this.niceViewManager = new NiceViewManager();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class TravelGoodServiceHolder {

        public static final ItineraryService INSTANCE = new TravelGoodService();
    }

    public static ItineraryService getInstance() {
        return TravelGoodServiceHolder.INSTANCE;
    }

    public Integer createItinerary(Itinerary it) {

        ItineraryEntity ite = new ItineraryEntity(it.getUserId(), getNextId(
                itineraryList),
                ItineraryStatus.PLANNING);

        this.itineraryList.add(ite);

        return ite.getId();
    }

    public List<Itinerary> getAllItineraries() {
        List<Itinerary> itList = new ArrayList<Itinerary>();
        for (ItineraryEntity ite : this.itineraryList) {
            itList.add(AssemblerUtility.toItinerary(ite));
        }

        return itList;
    }

    public List<Itinerary> getUserItineraries(String userId) {
        List<Itinerary> itList = new ArrayList<Itinerary>();
        for (ItineraryEntity ite : this.itineraryList) {
            if (ite.getUserId().equals(userId)) {
                itList.add(AssemblerUtility.toItinerary(ite));
            }
        }

        return itList;
    }

    public Itinerary getItinerary(Integer id) {
        return AssemblerUtility.toItinerary(getItineraryEntity(id));
        
    }

    public boolean deleteItinerary(Integer id) throws InvalidStatusException {

        Iterator<ItineraryEntity> it = itineraryList.iterator();

        while (it.hasNext()) {
            ItineraryEntity ite = it.next();
            if (ite.getId().equals(id)) {
                if (ItineraryStatus.PLANNING.equals(ite.getCurrentStatus())) {
                    it.remove();
                    return true;

                } else {
                    throw new InvalidStatusException();

                }
            }
        }

        return false;

    }

    protected ItineraryEntity getItineraryEntity(Integer id) {
        for (ItineraryEntity ite : this.itineraryList) {
            if (ite.getId().equals(id)) {
                return ite;
            }
        }

        return null;
    }

    public HotelBooking getHotelBooking(Integer itineraryId, Integer bookingId) {

        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        return AssemblerUtility.toHotelBooking(ite.getHotelBooking(bookingId));

    }

    public Integer addHotel(Integer itineraryId, HotelBooking hotelBooking) {
        
        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        if (ite == null) {
            return null;
        }

        if (ItineraryStatus.PLANNING.equals(ite.getCurrentStatus())) {
            Integer hotelId = getNextId(ite.getHotelBookingList());

            HotelBookingEntity hbe = AssemblerUtility.toHotelBookingEntity(hotelBooking);
            hbe.setId(hotelId);
            hbe.setBookingStatus(BookingStatus.UNCONFIRMED);

            ite.addHotel(hbe);

            return hotelId;

        } else {
            return null;
            
        }

    }

    public boolean deleteHotel(Integer itineraryId, Integer hotelBookingId) {
        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        if (ite == null) {
            return false;
        }

        if (ItineraryStatus.PLANNING.equals(ite.getCurrentStatus())) {
            ite.deleteHotel(hotelBookingId);
            return true;

        } else {
            return false;

        }

    }

    public FlightBooking getFlightBooking(Integer itineraryId, Integer bookingId) {
        
        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        return AssemblerUtility.toFlightBooking(ite.getFlightBooking(bookingId));
    }

    public Integer addFlight(Integer itineraryId, FlightBooking flightBooking) {

        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        if (ite == null) {
            return null;
        }

        if (ItineraryStatus.PLANNING.equals(ite.getCurrentStatus())) {
            Integer flightId = getNextId(ite.getFlightBookingList());

            FlightBookingEntity fbe = AssemblerUtility.toFlightBookingEntity(flightBooking);
            fbe.setId(flightId);
            fbe.setBookingStatus(BookingStatus.UNCONFIRMED);
            
            ite.addFlight(fbe);

            return flightId;

        } else {
            return null;

        }
    }

    public boolean deleteFlight(Integer itineraryId, Integer flightBookingId) {
        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        if (ite == null) {
            return false;
        }

        if (ItineraryStatus.PLANNING.equals(ite.getCurrentStatus())) {
            ite.deleteFlight(flightBookingId);
            return true;

        } else {
            return false;

        }
    }
    
    public boolean bookItinerary(Integer itineraryId, CreditCardInfo ccInfo) throws BookingException {
        
        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        // check if itinerary is in PLANNING phase
        if (ite.getCurrentStatus() != ItineraryStatus.PLANNING) {
            // cannot book as it is not in planning phase
            return false;

        }

        try {
            // book every single hotel (if not already booked)
            for (HotelBookingEntity hbe : ite.getHotelBookingList()) {
                if (!BookingStatus.CONFIRMED.equals(hbe.getBookingStatus())) {
                    this.niceViewManager.bookHotel(hbe.getBookingNumber(), ccInfo);
                    hbe.setBookingStatus(BookingStatus.CONFIRMED);

                }
            }

            // book every single flight (if not already booked)
            for (FlightBookingEntity fbe : ite.getFlightBookingList()) {
                if (!BookingStatus.CONFIRMED.equals(fbe.getBookingStatus())) {
                    this.lameDuckManager.bookFlight(fbe.getBookingNumber(), ccInfo);
                    fbe.setBookingStatus(BookingStatus.CONFIRMED);

                }
            }

        } catch (BookingException e) {
            // booking failure
            // cancel all previous bookings
            // delete

            List<BookingEntity> failedBookingCancels = cancelAllBookings(ite, ccInfo);

            if (failedBookingCancels.size() > 0) {

                // change status back to BOOKED, as some bookings could not have been cancelled
                ite.setCurrentStatus(ItineraryStatus.BOOKED);
                
                throw new BookingException("Itinerary booking failed , but the following bookings could not have been rolled back: " +
                        failedBookingCancels);
            }

            // keep status as PLANNING

            throw new BookingException("Itinerary booking failed, reverted to PLANNING status.");

        }

        // change the itinerary status to BOOKED
        ite.setCurrentStatus(ItineraryStatus.BOOKED);

        return true;

    }

    public boolean cancelItinerary(Integer itineraryId, CreditCardInfo ccInfo) throws BookingException {

        ItineraryEntity ite = this.getItineraryEntity(itineraryId);

        // check if itinerary is in BOOKED phase
        if (!ItineraryStatus.BOOKED.equals(ite.getCurrentStatus())) {
            // cannot cancel as it is not in booked phase
            return false;

        }

        List<BookingEntity> failedBookingCancels = cancelAllBookings(ite, ccInfo);

        if (failedBookingCancels.size() > 0) {

            // change status back to BOOKED

            throw new BookingException("Itinerary cancel failed for the following bookings: " +
                    failedBookingCancels);
        }

        // if all cancels are ok - delete itinerary
        this.itineraryList.remove(ite);

        return true;

    }

    private List<BookingEntity> cancelAllBookings(ItineraryEntity ite, CreditCardInfo ccInfo) {

        List<BookingEntity> failedBookingCancels = new ArrayList<BookingEntity>();

        // cancel all hotels
        for (HotelBookingEntity hbe : ite.getHotelBookingList()) {

            if (BookingStatus.CONFIRMED.equals(hbe.getBookingStatus())) {
                try {
                    niceViewManager.cancelHotel(hbe.getBookingNumber());
                    hbe.setBookingStatus(BookingStatus.CANCELLED);

                } catch (BookingException e) {
                    failedBookingCancels.add(hbe);
                }

            }
        }

        // cancel all flights
        for (FlightBookingEntity fbe : ite.getFlightBookingList()) {

            if (BookingStatus.CONFIRMED.equals(fbe.getBookingStatus())) {
                try {
                    lameDuckManager.cancelFlight(fbe.getBookingNumber(), fbe.getPrice(), ccInfo);
                    fbe.setBookingStatus(BookingStatus.CANCELLED);

                } catch (BookingException e) {
                    failedBookingCancels.add(fbe);

                }

            }
        }

        return failedBookingCancels;

    }

    public List<HotelBooking> getAvailableHotelBookings(Date dateFrom, Date dateTo,
            String city) {

        List<HotelBookingEntity> hbeList = niceViewManager.getHotels(dateFrom, dateTo, city);

        List<HotelBooking> hbList = new ArrayList<HotelBooking>();

        for (HotelBookingEntity hbe : hbeList) {
            hbList.add(AssemblerUtility.toHotelBooking(hbe));
        }

        return hbList;

    }

    public List<FlightBooking> getAvailableFlightBookings(Date date, String cityFrom,
            String cityTwo) {

        List<FlightBookingEntity> fbeList = lameDuckManager.getFlights(date, cityFrom, cityTwo);

        List<FlightBooking> fbList = new ArrayList<FlightBooking>();

        for (FlightBookingEntity fbe : fbeList) {
            fbList.add(AssemblerUtility.toFlightBooking(fbe));
        }

        return fbList;

    }



    protected Integer getNextId(List<? extends AbstractEntity> bookingList) {

        if (bookingList.size() == 0) {
            return new Integer(1);

        } else {
            Collections.sort(bookingList);

            Integer currentId = new Integer(1);
            for (AbstractEntity ob : bookingList) {
                if (!(ob.getId().equals(currentId))) {
                    // we've just found the first "empty" id to use
                    break;

                }

                ++currentId;
            }

            return currentId;

        }

    }

}
