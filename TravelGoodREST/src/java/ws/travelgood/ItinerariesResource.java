/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import java.net.URI;
import ws.travelgood.types.Itinerary;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import ws.travelgood.manager.ItineraryManager;
import ws.travelgood.manager.impl.TravelGoodManager;
import ws.travelgood.types.ItineraryStatus;

/**
 * REST Web Service
 *
 * @author mkucharek
 */
@Path("/itineraries")
public class ItinerariesResource {

    @Context
    private UriInfo context;

    /** Creates a new instance of ItinerariesResource */
    public ItinerariesResource() {
    }

    @GET
    @Produces("application/xml")
    public List<Itinerary> getCurrentItineraries() {
        return TravelGoodManager.getInstance().getAllItineraries();
    }

    @POST
    @Consumes("text/plain")
    public Response createItinearyRequest(String userId) {

        Itinerary it = TravelGoodManager.getInstance().createItinerary(new Itinerary(userId));

        URI uri = UriBuilder
                .fromUri(context.getBaseUri())
                .path(ItinerariesResource.class)
                .path(it.getId().toString())
                .build();

        return Response.status(Status.CREATED).location(uri).build();

    }

    /**
     * Sub-resource locator method for  {id}
     */
    @Path("{id}")
    public ItineraryResource getItineraryResource() {
        return new ItineraryResource();
    }
}
