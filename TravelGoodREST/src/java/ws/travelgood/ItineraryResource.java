/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import javax.ws.rs.Consumes;
import ws.travelgood.exceptions.InvalidStatusException;
import ws.travelgood.types.Itinerary;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.travelgood.types.ItineraryStatus;
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
    public Itinerary getXml(@PathParam("id") String itineraryId) {

        for (Itinerary i : ItinerariesResource.itineraryList) {
            if (i.getId().equals(itineraryId)) {
                return i;
            }
        }

        return null;

    }

    /**
     * PUT method for updating or creating an instance of ItineraryResource
     * @param id resource URI parameter
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public Response updateItineary(@PathParam("id") String id, Itinerary content) {

        boolean found = false;
        for (int i = 0; i < ItinerariesResource.itineraryList.size(); ++i) {

            Itinerary it = ItinerariesResource.itineraryList.get(i);
            if (it.getId().equals(id)) {
                found = true;
                ItinerariesResource.itineraryList.remove(i);

            }
        }

        if (!found) {
            return Response.status(Status.NOT_FOUND).build();
        }

        ItinerariesResource.itineraryList.add(content);

        return Response.ok(content).build();



    }

    /**
     * DELETE method for resource ItineraryResource
     * @param id resource URI parameter
     */
    @DELETE
    public Response delete(@PathParam("id") String id) {

        for (int i = 0; i < ItinerariesResource.itineraryList.size(); ++i) {

            Itinerary it = ItinerariesResource.itineraryList.get(i);
            if (it.getId().equals(id)) {
                if (it.getCurrentStatus().equals(ItineraryStatus.PLANNING)) {
                    ItinerariesResource.itineraryList.remove(i);
                    return Response.ok().build();

                } else {
                    throw new InvalidStatusException(
                            it +
                            " cannot be deleted as it is no longer in planning state");

                }
            }
        }

        return Response.status(Status.NOT_FOUND).build();
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
