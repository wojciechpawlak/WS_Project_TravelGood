/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource;

import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.net.URI;
import javax.ws.rs.DELETE;
import ws.travelgood.types.Itinerary;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import ws.travelgood.resource.flight.FlightsResource;
import ws.travelgood.resource.hotel.HotelsResource;
import ws.travelgood.manager.BookingException;
import ws.travelgood.service.InvalidStatusException;
import ws.travelgood.service.TravelGoodService;
import ws.travelgood.types.ItineraryStatus;
import ws.travelgood.types.banking.CreditCardInfo;

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
    public Response getItinerary(@PathParam("itineraryId") Integer itineraryId) {

        Itinerary it =
                TravelGoodService.getInstance().getItinerary(itineraryId);

        if (it != null) {
            return Response.ok(it).build();

        } else {
            return Response.status(Status.NOT_FOUND).build();
            
        }

    }

    @GET
    @Path("status")
    @Produces("application/xml")
    public ItineraryStatus getStatus(@PathParam("itineraryId") Integer id) {

        Itinerary it = TravelGoodService.getInstance().getItinerary(id);

        return it.getCurrentStatus();
    }

    @DELETE
    public Response cancelPlanning(@PathParam("itineraryId") Integer id) {

        try {
            boolean deleted = TravelGoodService.getInstance().deleteItinerary(id);

            if (deleted) {
                return Response.ok().build();

            } else {
                return Response.status(Status.NOT_FOUND).build();
            }

        } catch (InvalidStatusException e) {
            // cannot cancel - status is not PLANNING
            return Response.status(Status.BAD_REQUEST).build();

        }

    }

    @PUT
    @Path("book")
    public Response bookItinerary(@PathParam("itineraryId") Integer id, CreditCardInfo ccInfo) {

        try {
            boolean booked = TravelGoodService.getInstance().bookItinerary(id, ccInfo);

            if (!booked) {
                return Response.status(Status.BAD_REQUEST).build();
            }

            URI uri = UriBuilder
                    .fromResource(ItinerariesResource.class)
                    .segment(id.toString())
                    .build();

            return Response.ok().location(uri).build();

        } catch (BookingException e) {
            // booking failed, exception contains all the info

            return Response
                    .status(502)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

    }

    @PUT
    @Path("cancel")
    public Response cancelItinerary(@PathParam("itineraryId") Integer id, CreditCardInfo ccInfo) {

        try {
            boolean cancelled = TravelGoodService.getInstance().cancelItinerary(id, ccInfo);

            if (!cancelled) {
                return Response.status(Status.BAD_REQUEST).build();
            }

            return Response.ok().build();

        } catch (BookingException e) {
            // cancel failed
            return Response
                    .status(502)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
            
        }
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
