/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import ws.travelgood.domain.Itinerary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import ws.travelgood.domain.ItineraryStatus;

/**
 * REST Web Service
 *
 * @author mkucharek
 */
@Path("/itineraries")
public class ItinerariesResource {

    @Context
    private UriInfo context;
    public static List<Itinerary> itineraryList = new ArrayList<Itinerary>(
            Arrays.asList(
            new Itinerary[]{
                new Itinerary("u1", "1"),
                new Itinerary("u1", "2"),
                new Itinerary("u2", "3", ItineraryStatus.BOOKED)
            }));
    
    static int lastAssignedId = 3;

    /** Creates a new instance of ItinerariesResource */
    public ItinerariesResource() {
    }

    @GET
    @Produces("application/xml")
    public List<Itinerary> getCurrentItineraries() {
        return ItinerariesResource.itineraryList;
    }

    @POST
    @Path("create")
    @Consumes("text/plain")
    @Produces("application/xml")
    public Response createItinearyRequest(String userId) {

        String itineraryId = String.valueOf(++lastAssignedId);

        Itinerary i = new Itinerary(userId, itineraryId);
        ItinerariesResource.itineraryList.add(i);

        return Response.ok(i).build();

    }

    /**
     * Sub-resource locator method for  {id}
     */
    @Path("{id}")
    public ItineraryResource getItineraryResource() {
        return new ItineraryResource();
    }
}
