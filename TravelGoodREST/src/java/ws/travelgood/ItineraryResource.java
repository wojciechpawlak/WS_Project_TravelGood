/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import java.net.URI;
import ws.travelgood.types.Itinerary;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import ws.travelgood.flights.FlightsResource;
import ws.travelgood.hotels.HotelsResource;

/**
 * REST Web Service
 *
 * @author mkucharek
 */
public class ItineraryResource {

    @Context
    private UriInfo context;

    /** Creates a new instance of ItineraryResource */
    public ItineraryResource() {
    }

    /**
     * Retrieves representation of an instance of ws.travelgood.ItineraryResource
     * @param id resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public Response getItinerary(@PathParam("id") String itineraryId) {

        try {
            Itinerary it =  ItinerariesResource.itineraryDAO.getItinerary(Integer.parseInt(
                    itineraryId));

            return Response.ok(it).build();

        } catch (NumberFormatException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
            
        }

    }

    @POST
    @Path("book")
    public Response bookItinerary(@PathParam("id") Integer id) {


        ItinerariesResource.itineraryDAO.bookItinerary(id);

        boolean booked = ItinerariesResource.itineraryDAO.bookItinerary(id);

        if (!booked) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        URI uri = UriBuilder
                .fromResource(ItinerariesResource.class)
                .segment(id.toString())
                .build();

        return Response.temporaryRedirect(uri).build();

    }

    @POST
    @Path("cancel")
    public Response cancelItinerary(@PathParam("id") Integer id) {

        ItinerariesResource.itineraryDAO.bookItinerary(id);

        boolean cancelled = ItinerariesResource.itineraryDAO.bookItinerary(id);

        if (!cancelled) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        URI uri = UriBuilder
                .fromResource(ItinerariesResource.class)
                .segment(id.toString())
                .build();

        return Response.temporaryRedirect(uri).build();
    }

    @Path("hotels")
    public HotelsResource getHotelsResource() {
        return new HotelsResource();
    }

    @Path("flights")
    public FlightsResource getFlightsResource() {
        return new FlightsResource();
    }
}
