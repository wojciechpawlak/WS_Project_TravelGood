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
import junit.framework.Assert;
import org.junit.Before;
import ws.travelgood.states.BookingStatus;
import ws.travelgood.states.ItineraryStatus;
import ws.travelgood.types.Booking;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public abstract class AbstractTravelGoodRESTTest {

    protected WebResource itinerariesWebResource;

    protected Client client;

    @Before
    public void setUp() {
        client = Client.create();
        client.setFollowRedirects(true);

        itinerariesWebResource = client.resource(getItineraryResourceLocation());

    }

    protected abstract String getItineraryResourceLocation();

        protected String getId(URI resourceLocation) {
        URI uri = resourceLocation;
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);

    }

    protected ClientResponse getItinerary(String itineraryId) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .build();

        return client.resource(uri)
                .get(ClientResponse.class);
    }

    protected ClientResponse cancelPlanning(String itineraryId) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .build();

        return client.resource(uri)
                .delete(ClientResponse.class);
    }

    protected ClientResponse bookItinerary(String itineraryId, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("book")
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    protected ClientResponse cancelItinerary(String itineraryId, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("status")
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    protected ClientResponse addHotel(String itineraryId, HotelBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("hotels")
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    protected ClientResponse addFlight(String itineraryId, FlightBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(itineraryId)
                .segment("flights")
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    protected List<FlightBooking> getFlightList(String itineraryId, String cityFrom, String cityTo, String dateStr) {
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

    protected List<HotelBooking> getHotelList(String itineraryId, String city, String dateFromStr, String dateToStr) {
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

    /**
     * Used for validating itineraries (only the ones that didn't fail during booking or cancel)
     * @param it
     * @param expectedUserId
     * @param expectedStatus
     * @param expHotelBookingQuantity
     * @param expFlightBookingQuantity
     */
    protected void testValidItinerary(Itinerary it, String expectedUserId, ItineraryStatus expectedStatus, int expHotelBookingQuantity, int expFlightBookingQuantity) {
        Assert.assertNotNull(it);

        Assert.assertEquals(expectedUserId, it.getUserId());
        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

        Assert.assertEquals(expHotelBookingQuantity, it.getHotelBookingList().size());
        Assert.assertEquals(expFlightBookingQuantity, it.getFlightBookingList().size());

        BookingStatus expBookingStatus;
        if (expectedStatus == ItineraryStatus.PLANNING) {
            expBookingStatus = BookingStatus.UNCONFIRMED;

        } else {
            expBookingStatus = BookingStatus.CONFIRMED;

        }

        for (Booking b : it.getHotelBookingList()) {
            Assert.assertEquals(expBookingStatus, b.getBookingStatus());
        }

        for (Booking b : it.getFlightBookingList()) {
            Assert.assertEquals(expBookingStatus, b.getBookingStatus());
        }

    }

    protected void printClientRespone(ClientResponse response) {
        System.out.println("Status: " + response.getStatus());
        System.out.println("Location: " + response.getLocation());
        System.out.println("ResponseType: " + response.getType());
        System.out.println();

    }

}
