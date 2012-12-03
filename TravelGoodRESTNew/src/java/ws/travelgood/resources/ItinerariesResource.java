/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import ws.travelgood.service.ItineraryService;
import ws.travelgood.service.impl.ItineraryServiceImpl;
import ws.travelgood.types.ItinerariesRepresentation;

/**
 * REST Web Service
 *
 * @author mkucharek
 */
@Path("/{cid}")
public class ItinerariesResource {

    @Context
    private UriInfo context;

    private ItineraryService itService = ItineraryServiceImpl.getInstance();

    /** Creates a new instance of ItinerariesResource */
    public ItinerariesResource() {
    }

    @GET
    @Produces("application/xml")
    public ItinerariesRepresentation getCurrentItineraries(@PathParam("cid") String cid) {

        ItinerariesRepresentation isr = new ItinerariesRepresentation(itService.getUserItineraries(cid));
        
        // set links

        return isr;
    }

    /**
     * Sub-resource locator method for  {id}
     */
    @Path("{iid}")
    public ItineraryResource getItineraryResource() {
        return new ItineraryResource();
    }
}
