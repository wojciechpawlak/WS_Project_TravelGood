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
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightBooking;

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

        printClientRespone(createResponse);

        // getting itinerary id
        String idStr = getId(createResponse.getLocation());

        // getting our itinerary
        Itinerary itRet = client.resource(createResponse.getLocation()).get(Itinerary.class);

        testItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING);

        // getting flight list
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        // adding a flight to our itinerary
        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        printClientRespone(addFlightResponse);

        CreditCardInfo ccInfo = new CreditCardInfo();
             ccInfo.setName("Anne Strandberg");
             ccInfo.setNumber("50408816");
             ExpirationDateType edt = new ExpirationDateType();
             edt.setMonth(5);
             edt.setYear(9);
             ccInfo.setExpirationDate(edt);

        ClientResponse bookItineraryResponse = bookItinerary(idStr, ccInfo);
        printClientRespone(bookItineraryResponse);

        Assert.assertNotNull(bookItineraryResponse);

    }

    @Test
    public void testP2() {

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
        testItinerary(itRet, it.getUserId(), ItineraryStatus.PLANNING);

        // getting flight list
        List<FlightBooking> fbList = getFlightList(idStr, "Moscow", "Berlin", "2012-12-25");

        // adding a flight to our itinerary
        ClientResponse addFlightResponse = addFlight(idStr, fbList.get(0));
        printClientRespone(addFlightResponse);

        CreditCardInfo ccInfo = new CreditCardInfo();
             ccInfo.setName("Anne Strandberg");
             ccInfo.setNumber("50408816");
             ExpirationDateType edt = new ExpirationDateType();
             edt.setMonth(5);
             edt.setYear(9);
             ccInfo.setExpirationDate(edt);

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
                .segment("cancel")
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
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

    private void testItinerary(Itinerary it, String expectedUserId, ItineraryStatus expectedStatus) {
        Assert.assertNotNull(it);

        Assert.assertEquals(expectedUserId, it.getUserId());
        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

    }

    private void printClientRespone(ClientResponse response) {
        System.out.println("Status: " + response.getStatus());
        System.out.println("Location: " + response.getLocation());
        System.out.println("ResponseType: " + response.getType());
        System.out.println();

    }

}