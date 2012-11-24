/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.niceview.types.HotelType;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;

/**
 *
 * @author mkucharek
 */
public class TestTravelGoodREST {

    private final static String ITINERARY_RESOURCE_STR = "http://localhost:8080/TravelGoodREST/resources/itineraries";

    private WebResource itinerariesWebResource;

    private Client client;

    public TestTravelGoodREST() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        client = Client.create();
        client.setFollowRedirects(true);

        itinerariesWebResource = client.resource("http://localhost:8080/TravelGoodREST/resources/itineraries");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSample() {
        Client c = Client.create();
        c.setFollowRedirects(true);

        WebResource itineraries = c.resource("http://localhost:8080/TravelGoodREST/resources/itineraries");

        String response = itineraries.accept(
        MediaType.APPLICATION_XML_TYPE).
        header("X-FOO", "BAR").
        get(String.class);

        Assert.assertNotNull(response);

        List<HotelType> list = itineraries.get(new GenericType<List<HotelType>>(){});

        Assert.assertNotNull(list);

    }

    @Test
    public void testCreateItinerary() {

        // creating itinerary
        ClientResponse response = itinerariesWebResource
                .entity("u2", MediaType.TEXT_PLAIN_TYPE)
                .post(ClientResponse.class);

        // getting our itinerary
        Itinerary it = client.resource(response.getLocation()).get(Itinerary.class);

        testItinerary(it, "u2", ItineraryStatus.PLANNING);

    }

    private void testItinerary(Itinerary it, String expectedUserId, ItineraryStatus expectedStatus) {
        Assert.assertNotNull(it);

        Assert.assertEquals(expectedUserId, it.getUserId());
        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

    }

}