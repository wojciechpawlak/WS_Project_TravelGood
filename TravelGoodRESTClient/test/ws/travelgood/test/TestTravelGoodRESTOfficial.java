/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class TestTravelGoodRESTOfficial {

    private final static String ITINERARY_RESOURCE_STR = "http://localhost:8080/TravelGoodREST/resources/itineraries";

    private WebResource itinerariesWebResource;

    private Client client;

    public TestTravelGoodRESTOfficial() {
    }

    @Before
    public void setUp() {
        client = Client.create();
        client.setFollowRedirects(false);

        itinerariesWebResource = client.resource(ITINERARY_RESOURCE_STR);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testP1() {

        Itinerary it = new Itinerary("test");

        // creating itinerary
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        // validating it got created successfully
        Assert.assertEquals(201, createResponse.getStatus());

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
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        testItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, BookingStatus.UNCONFIRMED);


        // booking
        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");

        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        // verifying that the status has changed
        itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        testItinerary(itRet, it.getUserId(), ItineraryStatus.BOOKED, BookingStatus.CONFIRMED);

        Assert.assertNotNull(bookItineraryResponse);

    }

    @Test
    public void testP2() {

        Itinerary it = new Itinerary("test");

        // creating itinerary
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        // validating it got created successfully
        Assert.assertEquals(201, createResponse.getStatus());

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        testItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, BookingStatus.UNCONFIRMED);

        // getting flight list


        // adding a flight
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        // cancel planning
        ClientResponse cancelPlanningResponse = cancelPlanning(idStr);
        Assert.assertEquals(200, cancelPlanningResponse.getStatus());

        // verify that the itinerary does not exist anymore
        ClientResponse resp = getItinerary(idStr);
        Assert.assertEquals(404, resp.getStatus());


    }

    @Test
    public void testC1() {

        // creating itinerary
        Itinerary it = new Itinerary("test");
        
        ClientResponse createResponse = itinerariesWebResource
                .entity(it, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);

        Assert.assertEquals(201, createResponse.getStatus());

        printClientRespone(createResponse);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        // validating that the itinerary is correct
        testItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING, BookingStatus.UNCONFIRMED);

        // getting flight list
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        // adding a flight to our itinerary
        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        Assert.assertEquals(201, addFlightResponse.getStatus());

        CreditCardInfo ccInfo = new CreditCardInfo(new ExpirationDate(5,9),
                "Anne Strandberg", "50408816");

        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        printClientRespone(bookItineraryResponse);

        Assert.assertEquals(200, bookItineraryResponse.getStatus());

        ClientResponse cancelItineraryResponse = cancelItinerary(idStr, ccInfo);

        Assert.assertEquals(200, cancelItineraryResponse.getStatus());

    }

    private String getId(URI resourceLocation) {
        URI uri = resourceLocation;
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);

    }

    private ClientResponse getItinerary(String itineraryId) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .build();

        return client.resource(uri)
                .get(ClientResponse.class);
    }

    private ClientResponse cancelPlanning(String itineraryId) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .build();

        return client.resource(uri)
                .delete(ClientResponse.class);
    }

    private ClientResponse bookItinerary(String itineraryId, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("book")
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    private ClientResponse cancelItinerary(String itineraryId, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("status")
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    private ClientResponse addHotel(String itineraryId, HotelBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("hotels")
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    private ClientResponse addFlight(String itineraryId, FlightBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("flights")
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    private List<FlightBooking> getFlightList(String itineraryId, String cityFrom, String cityTo, String dateStr) {
        // getting the list of flights
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("cityFrom", cityFrom);
        params.add("cityTo", cityTo);
        params.add("date", dateStr);


        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("flights")
                .build();

        List<FlightBooking> fbList = client.resource(uri)
                .queryParams(params)
                .get(new GenericType<List<FlightBooking>>(){});

        Assert.assertNotNull(fbList);
        Assert.assertTrue(!fbList.isEmpty());

        return fbList;

    }

    private List<HotelBooking> getHotelList(String itineraryId, String city, String dateFromStr, String dateToStr) {
        // getting the list of flights
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("city", city);
        params.add("dateFrom", dateFromStr);
        params.add("dateTo", dateToStr);


        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("hotels")
                .build();

        List<HotelBooking> fbList = client.resource(uri)
                .queryParams(params)
                .get(new GenericType<List<HotelBooking>>(){});

        Assert.assertNotNull(fbList);
        Assert.assertTrue(!fbList.isEmpty());

        return fbList;

    }

    private void testItinerary(Itinerary it, String expectedUserId, ItineraryStatus expectedStatus, BookingStatus expBookingStatus) {
        Assert.assertNotNull(it);

        Assert.assertEquals(expectedUserId, it.getUserId());
        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

        for (Booking b : it.getHotelBookingList()) {
            Assert.assertEquals(expBookingStatus, b.getBookingStatus());
        }

        for (Booking b : it.getFlightBookingList()) {
            Assert.assertEquals(expBookingStatus, b.getBookingStatus());
        }

    }

    private void printClientRespone(ClientResponse response) {
        System.out.println("Status: " + response.getStatus());
        System.out.println("Location: " + response.getLocation());
        System.out.println("ResponseType: " + response.getType());
        System.out.println();

    }

}