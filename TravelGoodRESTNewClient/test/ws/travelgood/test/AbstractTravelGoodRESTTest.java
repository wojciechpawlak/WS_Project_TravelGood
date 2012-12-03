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
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.booking.AbstractBooking;
import ws.travelgood.domain.booking.BookingStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
public abstract class AbstractTravelGoodRESTTest {

    protected WebResource itinerariesWebResource;

    protected Client client;

    protected static final String BOOKING_SEGMENT = "booking";
    protected static final String STATUS_SEGMENT = "status";
    protected static final String HOTELS_SEGMENT = "hotels";
    protected static final String FLIGHTS_SEGMENT = "flights";

    @Before
    public void setUp() {
        client = Client.create();
        client.setFollowRedirects(true);

        itinerariesWebResource = client.resource(getItineraryResourceLocation());

    }

    protected abstract String getItineraryResourceLocation();

    // utility methods

    /**
     * Extracts the resource identifier from the URI (obviously, it has to end with an identifier, like itineraries/1)
     * @param resourceLocation
     * @return
     */
    protected String getId(URI resourceLocation) {
        URI uri = resourceLocation;
        String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);

    }

    protected ClientResponse getItinerary(String cid, String iid) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .build();

        return client.resource(uri)
                .get(ClientResponse.class);
    }

    protected ClientResponse createItinerary(String cid, String iid, Itinerary it) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .build();

        return client.resource(uri)
                .entity(it, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);

    }

    protected ClientResponse cancelPlanning(String cid, String iid) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .build();

        return client.resource(uri)
                .delete(ClientResponse.class);
    }

    protected ClientResponse bookItinerary(String cid, String iid, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(BOOKING_SEGMENT)
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    protected ClientResponse cancelItinerary(String cid, String iid, CreditCardInfo ccInfo) {
        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(STATUS_SEGMENT)
                .build();

        return client.resource(uri)
                .entity(ccInfo, MediaType.APPLICATION_XML)
                .put(ClientResponse.class);
    }

    protected ClientResponse addHotel(String cid, String iid, HotelBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(HOTELS_SEGMENT)
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    protected ClientResponse addFlight(String cid, String iid, FlightBooking fb) {

        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(FLIGHTS_SEGMENT)
                .build();

        return client.resource(uri)
                .entity(fb, MediaType.APPLICATION_XML)
                .post(ClientResponse.class);
    }

    protected List<FlightBooking> getFlightList(String cid, String iid, String cityFrom, String cityTo, String dateStr) {
        // getting the list of flights
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("cityFrom", cityFrom);
        params.add("cityTo", cityTo);
        params.add("date", dateStr);


        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(FLIGHTS_SEGMENT)
                .build();

        List<FlightBooking> fbList = client.resource(uri)
                .queryParams(params)
                .get(new GenericType<List<FlightBooking>>(){});

        Assert.assertNotNull(fbList);
        Assert.assertTrue(!fbList.isEmpty());

        return fbList;

    }

    protected List<HotelBooking> getHotelList(String cid, String iid, String city, String dateFromStr, String dateToStr) {
        // getting the list of flights
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("city", city);
        params.add("dateFrom", dateFromStr);
        params.add("dateTo", dateToStr);


        URI uri = UriBuilder.fromUri(itinerariesWebResource.getURI())
                .segment(cid)
                .segment(iid)
                .segment(HOTELS_SEGMENT)
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

        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

        Assert.assertEquals(expHotelBookingQuantity, it.getHotelBookingList().size());
        Assert.assertEquals(expFlightBookingQuantity, it.getFlightBookingList().size());

        BookingStatus expBookingStatus;
        if (expectedStatus == ItineraryStatus.PLANNING) {
            expBookingStatus = BookingStatus.UNCONFIRMED;

        } else {
            expBookingStatus = BookingStatus.CONFIRMED;

        }

        for (AbstractBooking b : it.getHotelBookingList()) {
            Assert.assertEquals(expBookingStatus, b.getBookingStatus());
        }

        for (AbstractBooking b : it.getFlightBookingList()) {
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
