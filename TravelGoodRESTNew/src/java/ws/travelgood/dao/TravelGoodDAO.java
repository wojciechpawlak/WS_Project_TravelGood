/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ws.travelgood.domain.Customer;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.booking.AbstractBooking;
import ws.travelgood.domain.booking.BookingStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 * @author wpawlak
 */
public class TravelGoodDAO implements ItineraryDAO {

    private Map<String, Customer> idCustomerMap;

    private TravelGoodDAO() {
        this.idCustomerMap = new HashMap<String, Customer>();

        Customer c = new Customer("test");
        Itinerary it = new Itinerary("test", ItineraryStatus.PLANNING);
        it.addFlight(new FlightBooking(BookingStatus.UNCONFIRMED, "123", 10.1));
        c.addUpdateItinerary(it);

        this.idCustomerMap.put(c.getId(), c);
    }

    /**
     * SingletonHolder is loaded on the first execution of getInstance()
     * or the first access to Holder.INSTANCE, not before.
     */
    private static class SingletonHolder {

        public static final ItineraryDAO INSTANCE = new TravelGoodDAO();
    }

    public static ItineraryDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Itinerary> getItineraries(String cid) {
        try {
            return new ArrayList<Itinerary>(idCustomerMap.get(cid).getItineraryMap().values());

        } catch (NullPointerException e) {
            if (cid == null) {
                throw e;

            } else {
                return null;
            }
        }
    }

    public Itinerary getItinerary(String cid, String iid) {

        Itinerary it = getActualItinerary(cid, iid);

        if (it != null) {
            return new Itinerary(it);

        } else {
            return null;
        }
         
    }

    public boolean putItinerary(String cid, Itinerary it) {

        if (it == null) {
            throw new NullPointerException("Cannot put null as itinerary");
            
        }

        try {

            if (idCustomerMap.get(cid) == null) {
                idCustomerMap.put(cid, new Customer(cid));
                
            }

            idCustomerMap.get(cid).getItineraryMap().put(it.getId(), it);
            return true;

        } catch (NullPointerException e) {
            if (cid == null) {
                throw e;

            } else {
                return false;
            }
        }

    }

    public boolean deleteItinerary(String cid, String iid) {
        try {
            idCustomerMap.get(cid).getItineraryMap().remove(iid);
            return true;

        } catch (NullPointerException e) {
            if (cid == null) {
                return false;

            } else {
                throw e;
            }
        }
    }

    public boolean addBooking(String cid, String iid, AbstractBooking booking) {

        if (booking.getClass().isInstance(new HotelBooking())) {

            Itinerary it = this.getActualItinerary(cid, iid);

            if (it == null) {
                return false;
            }

            deleteBooking(cid, iid, booking);

            it.getHotelBookingList().add((HotelBooking) booking);

            return true;

        } else if (booking.getClass().isInstance(new FlightBooking())) {

            Itinerary it = this.getActualItinerary(cid, iid);

            if (it == null) {
                return false;
            }

            deleteBooking(cid, iid, booking);

            it.getFlightBookingList().add((FlightBooking) booking);

            return true;

        } else {
            // probably should throw some exception
            return false;
        }
    }

    public boolean deleteBooking(String cid, String iid, AbstractBooking booking) {

        if (booking.getClass().isInstance(new HotelBooking())) {

            Itinerary it = this.getActualItinerary(cid, iid);

            if (it == null) {
                return false;
            }

            Iterator<HotelBooking> i = it.getHotelBookingList().iterator();

            while (i.hasNext()) {
                HotelBooking ihb = i.next();

                if (ihb.getId().equals(booking.getId())) {
                    i.remove();
                    return true;
                }
            }

        } else if (booking.getClass().isInstance(new HotelBooking())) {
            
            Itinerary it = this.getActualItinerary(cid, iid);

            if (it == null) {
                return false;
            }

            Iterator<FlightBooking> i = it.getFlightBookingList().iterator();

            while (i.hasNext()) {
                FlightBooking ihb = i.next();

                if (ihb.getId().equals(booking.getId())) {
                    i.remove();
                    return true;
                }
            }

        } else {
            // probably should throw some exception

        }

        return false;

    }

    private Itinerary getActualItinerary(String cid, String iid) {

        try {
            return idCustomerMap.get(cid).getItineraryMap().get(
                    iid);

        } catch (NullPointerException e) {
            if (cid == null || iid == null) {
                throw e;

            } else {
                return null;

            }
        }

    }

}
