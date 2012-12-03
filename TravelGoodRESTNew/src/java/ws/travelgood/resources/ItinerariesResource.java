/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.service.ItineraryService;
import ws.travelgood.service.impl.ItineraryServiceImpl;
import ws.travelgood.statuses.ItinerariesStatusRepresentation;

/**
 * REST Web Service
 *
 * @author mkucharek
 */
@Path("/itineraries/{cid}")
public class ItinerariesResource {

    @Context
    private UriInfo context;

    private ItineraryService itService = ItineraryServiceImpl.getInstance();

    /** Creates a new instance of ItinerariesResource */
    public ItinerariesResource() {
    }

    @GET
    @Produces("application/xml")
    public Response getCurrentItineraries(@PathParam("cid") String cid) {

        List<Itinerary> itList = itService.getUserItineraries(cid);
        
        if (itList == null) {
            return Response
                    .status(Status.NOT_FOUND)
                    .entity("User not found")
                    .build();
        }

        ItinerariesStatusRepresentation isr = new ItinerariesStatusRepresentation(itList);
        
        // set up links

        return Response.ok(isr).build();
    }

    /**
     * Sub-resource locator method for  {id}
     */
    @Path("{iid}")
    public ItineraryResource getItineraryResource() {
        return new ItineraryResource();
    }
}
