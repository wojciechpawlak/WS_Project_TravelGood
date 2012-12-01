/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource;

import ws.travelgood.*;
import java.net.URI;
import ws.travelgood.types.Itinerary;
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
import ws.travelgood.service.TravelGoodService;

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
        return TravelGoodService.getInstance().getAllItineraries();
    }

    @POST
    @Consumes("application/xml")
    public Response createItinearyRequest(Itinerary itinerary) {

        Integer id = TravelGoodService.getInstance().createItinerary(itinerary);

        URI uri = UriBuilder
                .fromUri(context.getBaseUri())
                .path(ItinerariesResource.class)
                .path(id.toString())
                .build();

        return Response.status(Status.CREATED).location(uri).build();

    }

    /**
     * Sub-resource locator method for  {id}
     */
    @Path("{itineraryId}")
    public ItineraryResource getItineraryResource() {
        return new ItineraryResource();
    }
}
