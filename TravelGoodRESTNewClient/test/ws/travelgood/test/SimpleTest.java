/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.test;

import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.banking.ExpirationDate;
import ws.travelgood.domain.booking.AbstractBooking;
import ws.travelgood.domain.booking.BookingStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;
import ws.travelgood.types.ItineraryRepresentation;

/**
 *
 * @author mkucharek
 */
public class SimpleTest extends AbstractTravelGoodRESTTest {

    private final static String ITINERARY_RESOURCE_STR = "http://localhost:8080/TravelGoodRESTNew/resources";

    @Override
    protected String getItineraryResourceLocation() {
        return ITINERARY_RESOURCE_STR;
    }

    @Test
    public void test() {

        String cid = "B";
        String iid = "IB";

        Itinerary it = new Itinerary();

        // creating itinerary
        ClientResponse createResponse = createItinerary(cid, iid, it);

        // validating it got created successfully
        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary to verify
        ClientResponse itRep = getItinerary(cid, iid);

        ItineraryRepresentation ir = itRep.getEntity(ItineraryRepresentation.class);

        testValidItinerary(it, "", ItineraryStatus.PLANNING, 0, 0);

        // adding a CANCEL_FAILING flight
        List<FlightBooking> fbList = getFlightList(cid, iid, "CancelCity", "Paris", "2012-12-23");
        Assert.assertEquals(1, fbList.size());

        ClientResponse addFlightResponse = addFlight(cid, iid, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());


        // adding a hotel
        List<HotelBooking> hbList = getHotelList(cid, iid, "Vienna", "2012-12-23", "2012-12-25");
        Assert.assertEquals(2, hbList.size());

        ClientResponse addHotelResponse = addHotel(cid, iid, hbList.get(0));
        Assert.assertEquals(201, addHotelResponse.getStatus());

        // adding a flight
        fbList = getFlightList(cid, iid, "Copenhagen", "Bucharest", "2012-12-22");

        addFlightResponse = addFlight(cid, iid, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // getting our itinerary to verify
        Itinerary itRet = getItinerary(cid, iid).getEntity(ItineraryRepresentation.class).getItinerary();
        testValidItinerary(itRet, "", ItineraryStatus.PLANNING, 1, 2);


        // booking
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");

        ClientResponse bookItineraryResponse = bookItinerary(cid, iid, ccInfo);
        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        // verifying that the status has changed
        itRet = getItinerary(cid, iid).getEntity(ItineraryRepresentation.class).getItinerary();
        testValidItinerary(itRet, "", ItineraryStatus.BOOKED, 1, 2);

        // cancelling (should fail)
        ClientResponse cancelItineraryResponse = cancelItinerary(cid, iid, ccInfo);
        Assert.assertEquals(502, cancelItineraryResponse.getStatus());

        // verifying manually
        itRet = getItinerary(cid, iid).getEntity(ItineraryRepresentation.class).getItinerary();

        Assert.assertNotNull(itRet);

        Assert.assertEquals(ItineraryStatus.BOOKED, itRet.getCurrentStatus());

        Assert.assertEquals(1, itRet.getHotelBookingList().size());
        Assert.assertEquals(2, itRet.getFlightBookingList().size());


        // hotel should be cancelled
        for (AbstractBooking b : itRet.getHotelBookingList()) {
            Assert.assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
        }

        // failed flight should be confirmed, second one should be cancelled
        for (AbstractBooking b : itRet.getFlightBookingList()) {
            if (b.getBookingNumber().equals("456CANCEL")) {
                Assert.assertEquals(BookingStatus.CONFIRMED, b.getBookingStatus());

            } else {
                Assert.assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());

            }
        }
    }

}
