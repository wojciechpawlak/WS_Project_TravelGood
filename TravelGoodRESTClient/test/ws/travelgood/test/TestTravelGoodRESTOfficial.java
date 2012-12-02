/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.test;

import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Assert;
import org.junit.Test;
import ws.travelgood.states.BookingStatus;
import ws.travelgood.states.ItineraryStatus;
import ws.travelgood.types.Booking;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.banking.ExpirationDate;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public class TestTravelGoodRESTOfficial extends AbstractTravelGoodRESTTest {

    private final static String ITINERARY_RESOURCE_STR = "http://localhost:8080/TravelGoodREST/resources/itineraries";

    @Override
    protected String getItineraryResourceLocation() {
        return ITINERARY_RESOURCE_STR;
    }
    
    @Test
    public void testP1() {

        Itinerary it = new Itinerary("P1");

        // creating itinerary
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        // validating it got created successfully
        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary to verify
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 0);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // adding a flight
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a hotel
        List<HotelBooking> hbList = getHotelList(idStr, "Vienna", "2012-12-23", "2012-12-25");
        Assert.assertEquals(2, hbList.size());

        ClientResponse addHotelResponse = addHotel(idStr, hbList.get(0));
        Assert.assertEquals(201, addHotelResponse.getStatus());

        // adding a flight
        fbList = getFlightList(idStr, "Copenhagen", "Bucharest", "2012-12-22");

        addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a flight
        fbList = getFlightList(idStr, "Paris", "Tokyo", "2012-12-29");

        addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a hotel
        addHotelResponse = addHotel(idStr, hbList.get(1));
        Assert.assertEquals(201, addHotelResponse.getStatus());


        // getting our itinerary to verify
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 2, 3);


        // booking
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");

        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        // verifying that the status has changed
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.BOOKED, 2, 3);

    }

    @Test
    public void testP2() {

        Itinerary it = new Itinerary("P2");

        // creating itinerary
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        // validating it got created successfully
        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 0);
        
        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // adding a flight
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // getting our itinerary
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 1);

        // cancel planning
        ClientResponse cancelPlanningResponse = cancelPlanning(idStr);
        Assert.assertEquals(200, cancelPlanningResponse.getStatus());

        // verify that the itinerary does not exist anymore
        ClientResponse resp = getItinerary(idStr);
        Assert.assertEquals(404, resp.getStatus());


    }

    @Test
    public void testB() {

        // creating itinerary
        Itinerary it = new Itinerary("B");

        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 0);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // adding a FAILING flight to our itinerary (since hotels are processed before the flights, this one will be processed as second booking)
        List<FlightBooking> fbList = getFlightList(idStr, "FailCity", "Paris", "2012-12-23");
        // changing booking number to make it fail
        fbList.get(0).setBookingNumber("not_found");

        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a hotel
        List<HotelBooking> hbList = getHotelList(idStr, "Vienna", "2012-12-23", "2012-12-25");
        Assert.assertEquals(2, hbList.size());

        ClientResponse addHotelResponse = addHotel(idStr, hbList.get(0));
        Assert.assertEquals(201, addHotelResponse.getStatus());

        // adding a flight
        fbList = getFlightList(idStr, "Copenhagen", "Bucharest", "2012-12-22");

        addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // getting our itinerary
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 1, 2);


        // booking (should fail)
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");
        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        Assert.assertEquals(502, bookItineraryResponse.getStatus());

        // getting our itinerary to verify
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        // verifying manually
        Assert.assertNotNull(itRet);

        Assert.assertEquals(it.getUserId(), itRet.getUserId());
        Assert.assertEquals(ItineraryStatus.PLANNING, itRet.getCurrentStatus());

        Assert.assertEquals(1, itRet.getHotelBookingList().size());
        Assert.assertEquals(2, itRet.getFlightBookingList().size());

        
        // hotel should be cancelled
        for (Booking b : itRet.getHotelBookingList()) {
            Assert.assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
        }

        //flights should be unconfirmed
        for (Booking b : itRet.getFlightBookingList()) {
            Assert.assertEquals(BookingStatus.UNCONFIRMED, b.getBookingStatus());
        }
        

    }

    @Test
    public void testC1() {

        // creating itinerary
        Itinerary it = new Itinerary("C1");
        
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 0);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // getting flight list
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        // adding a flight to our itinerary
        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a hotel
        List<HotelBooking> hbList = getHotelList(idStr, "Vienna", "2012-12-23", "2012-12-25");
        Assert.assertEquals(2, hbList.size());

        ClientResponse addHotelResponse = addHotel(idStr, hbList.get(0));
        Assert.assertEquals(201, addHotelResponse.getStatus());

        // adding a flight
        fbList = getFlightList(idStr, "Copenhagen", "Bucharest", "2012-12-22");

        addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // getting our itinerary
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 1, 2);


        // booking
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");
        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        // getting our itinerary to verify
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.BOOKED, 1, 2);

        // cancelling
        ClientResponse cancelItineraryResponse = cancelItinerary(idStr, ccInfo);
        Assert.assertEquals(200, cancelItineraryResponse.getStatus());

        // verify that the itinerary does not exist anymore
        ClientResponse resp = getItinerary(idStr);
        Assert.assertEquals(404, resp.getStatus());

    }

    @Test
    public void testC2() {

        // creating itinerary
        Itinerary it = new Itinerary("C2");

        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        Assert.assertEquals(201, createResponse.getStatus());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 0, 0);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // adding a CANCEL_FAILING flight to our itinerary (since hotels are processed before the flights during both booking & cancel, this one will be processed as second booking)
        List<FlightBooking> fbList = getFlightList(idStr, "CancelCity", "Paris", "2012-12-23");

        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // adding a hotel
        List<HotelBooking> hbList = getHotelList(idStr, "Vienna", "2012-12-23", "2012-12-25");
        Assert.assertEquals(2, hbList.size());

        ClientResponse addHotelResponse = addHotel(idStr, hbList.get(0));
        Assert.assertEquals(201, addHotelResponse.getStatus());

        // adding a flight
        fbList = getFlightList(idStr, "Copenhagen", "Bucharest", "2012-12-22");

        addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // getting our itinerary
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, 1, 2);


        // booking
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");
        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        // getting our itinerary to verify
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);
        testValidItinerary(itRet, it.getUserId(), ItineraryStatus.BOOKED, 1, 2);

        // cancelling (should fail)
        ClientResponse cancelItineraryResponse = cancelItinerary(idStr, ccInfo);
        Assert.assertEquals(502, cancelItineraryResponse.getStatus());

        // getting our itinerary to verify
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        // verifying manually
        Assert.assertNotNull(itRet);

        Assert.assertEquals(it.getUserId(), itRet.getUserId());
        Assert.assertEquals(ItineraryStatus.BOOKED, itRet.getCurrentStatus());

        Assert.assertEquals(1, itRet.getHotelBookingList().size());
        Assert.assertEquals(2, itRet.getFlightBookingList().size());


        // hotel should be cancelled
        for (Booking b : itRet.getHotelBookingList()) {
            Assert.assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());
        }

        // failed flight should be confirmed, second one should be cancelled
        for (Booking b : itRet.getFlightBookingList()) {
            if (b.getBookingNumber().equals("456CANCEL")) {
                Assert.assertEquals(BookingStatus.CONFIRMED, b.getBookingStatus());

            } else {
                Assert.assertEquals(BookingStatus.CANCELLED, b.getBookingStatus());

            }
        }

    }

}