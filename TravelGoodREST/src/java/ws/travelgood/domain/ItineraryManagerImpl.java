/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.lameduck.LameDuckWSDLPortType;
import ws.lameduck.LameDuckWSDLService;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.RequestGetFlightType;
import ws.niceview.types.HotelListType;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;
import ws.travelgood.types.flight.FlightList;
import ws.travelgood.types.hotel.HotelList;

/**
 *
 * @author mkucharek
 */
public class ItineraryManagerImpl implements ItineraryManager {

    protected List<Itinerary> itineraryList;
    protected int nextId;
    // internal usage only
    private final int ITINERARY_NOT_FOUND = -1;

    public ItineraryManagerImpl() {
        this.itineraryList = new ArrayList<Itinerary>();
        this.nextId = 1;

    }

    public ItineraryManagerImpl(List<Itinerary> initialItineraryList) {
        this();

        if (initialItineraryList != null) {

            for (Itinerary it : initialItineraryList) {
                this.createItinerary(it, nextId++);
            }

            this.nextId = this.itineraryList.size() + 1;

        }
    }

    public List<Itinerary> getAllItineraries() {
        return Collections.unmodifiableList(this.itineraryList);

    }

    public Itinerary getItinerary(Integer id) {

        for (Itinerary it : this.itineraryList) {
            if (it.getId().equals(id)) {
                return new Itinerary(it);
            }
        }

        return null;

    }

    public List<Itinerary> getUserItineraries(String userId) {

        List<Itinerary> retList = new ArrayList<Itinerary>();
        for (Itinerary it : this.itineraryList) {
            if (it.getUserId().equals(userId)) {
                retList.add(it);
            }
        }

        return retList;

    }

    public Itinerary createItinerary(Itinerary itinerary) {
        return this.createItinerary(itinerary, nextId++);

    }

    public HotelList getHotels(Date dateFrom, Date dateTo, String cityName) {

        ws.niceview.NiceViewWSDLService service =
                new ws.niceview.NiceViewWSDLService();
        ws.niceview.NiceViewWSDLPortType port = service.getNiceViewWSDLPort();

        GregorianCalendar dateFromGCal = new GregorianCalendar();
        dateFromGCal.setTime(dateFrom);

        GregorianCalendar dateToGCal = new GregorianCalendar();
        dateToGCal.setTime(dateFrom);

        XMLGregorianCalendar departureDate;
        XMLGregorianCalendar arrivalDate;

        try {
            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(dateFromGCal);

            arrivalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    dateToGCal);

        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
            
        }

        HotelListType result = port.getHotels(cityName, departureDate,
                arrivalDate);

        return new HotelList(result.getHotel());

    }

    public boolean addHotel(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        if (it.getHotelBooking(hotelBookingNumber) != null) {
            // hotel already added
            return false;
        }

        it.addHotel(hotelBookingNumber);

        return this.updateItinerary(it);

    }

    public boolean deleteHotel(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        it.deleteHotel(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public FlightList getFlights(Date date, String cityFromName,
            String cityToName) {

        LameDuckWSDLService service =
                new LameDuckWSDLService();
        LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();

        XMLGregorianCalendar departureDate;

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(df.format(date));

        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }

        RequestGetFlightType input = new RequestGetFlightType();

        input.setFlightDate(departureDate);
        input.setFlightStart(cityFromName);
        input.setFlightDestination(cityToName);

        FlightInformationListType result = port.getFlights(input);

        return new FlightList(result.getFlightInformation());

    }

    public boolean addFlight(Integer itineraryId, String hotelBookingNumber) {
        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        if (it.getFlightBooking(hotelBookingNumber) != null) {
            // flight already added
            return false;
        }

        it.addFlight(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public boolean deleteFlight(Integer itineraryId, String hotelBookingNumber) {

        Itinerary it = this.getItinerary(itineraryId);

        if (it == null) {
            return false;
        }

        it.deleteFlight(hotelBookingNumber);

        return this.updateItinerary(it);
    }

    public boolean bookItinerary(Integer itineraryId) {

        // check if itinerary is in PLANNING phase

        // optionally - check if all the items are UNCONFIRMED

        // book every single hotel

        // book every single flight

        // in case of failure - cancel all the previous bookings, return to PLANNING phase

        return false;
    }

    public boolean cancelItinerary(Integer itineraryId) {

        // cancel all hotels

        // cancel all flights

        // on error - continue, but notify the user

        return false;
    }

    protected boolean updateItinerary(Itinerary it) {

        if (!it.getCurrentStatus().equals(ItineraryStatus.PLANNING)) {
            // cannot update itinerary
            return false;
        }

        int index = this.getItineraryIndex(it);

        if (index != ITINERARY_NOT_FOUND) {

            this.itineraryList.set(index, it);
            return true;

        } else {
            // item must have been deleted - cannot update
            return false;

        }
    }

    protected boolean deleteItinerary(Integer id) {
        return this.deleteItinerary(this.getItinerary(id));

    }

    protected boolean deleteItinerary(Itinerary itinerary) {
        if (itinerary.getId() == null || itinerary.getId().compareTo(nextId) >=
                0) {
            // invalid itinerary passed

            return false;

        } else {
            // delete

            int index = this.getItineraryIndex(itinerary);

            if (index != ITINERARY_NOT_FOUND) {

                this.itineraryList.remove(index);
                return true;



            } else {
                // item must have been deleted - cannot delete
                return false;
            }

        }
    }

    protected Itinerary createItinerary(Itinerary it, int id) {
        it.setId(Integer.valueOf(id));
        this.itineraryList.add(it);

        return it;

    }

    protected int getItineraryIndex(Itinerary it) {

        for (int i = 0; i < this.itineraryList.size(); ++i) {
            if (this.itineraryList.get(i).getId().equals(it.getId())) {
                return i;

            }
        }

        // not found
        return ITINERARY_NOT_FOUND;

    }
}
