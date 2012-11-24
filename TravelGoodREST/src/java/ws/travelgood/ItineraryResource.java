/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

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
    public Response bookItinerary(@PathParam("id") String id) {

        // check if itinerary is in PLANNING phase

        // optionally - check if all the items are UNCONFIRMED

        // book every single hotel

        // book every single flight

        // in case of failure - cancel all the previous bookings, return to PLANNING phase

        return Response.ok().build();

    }

    @POST
    @Path("cancel")
    public Response cancelItinerary(@PathParam("id") String id) {

        // cancel all hotels

        // cancel all flights

        // on error - continue, but notify the user


        return Response.ok().build();
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
