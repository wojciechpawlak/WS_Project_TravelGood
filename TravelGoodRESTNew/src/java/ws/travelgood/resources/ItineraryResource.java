/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.manager.BookingException;
import ws.travelgood.resources.flight.FlightsResource;
import ws.travelgood.resources.hotel.HotelsResource;
import ws.travelgood.service.InvalidStatusException;
import ws.travelgood.service.ItineraryService;
import ws.travelgood.service.impl.ItineraryServiceImpl;
import ws.travelgood.statuses.ItineraryStatusRepresentation;
import ws.travelgood.statuses.ItineraryStatusStatusRepresentation;


/**
 * REST Web Service
 *
 * @author mkucharek
 */
public class ItineraryResource {

    @Context
    private UriInfo context;

    private ItineraryService itService = ItineraryServiceImpl.getInstance();

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
    public Response getItinerary(@PathParam("cid") String cid,
            @PathParam("iid") String iid) {

        Itinerary it = itService.getItinerary(cid, iid);

        if (it == null) {
            return Response.status(Status.NOT_FOUND).build();
            
        }

        ItineraryStatusRepresentation ir = new ItineraryStatusRepresentation(itService.
                getItinerary(cid, iid));

        // set up links

        return Response.ok(ir).build();


    }

    @PUT
    @Consumes("application/xml")
    public Response createItinearyRequest(
            @PathParam("cid") String cid,
            @PathParam("iid") String iid,
            Itinerary it) {

        // setting itinerary id to match the pathparam
        it.setId(iid);
        
        Itinerary itNew = itService.createItinerary(cid, it);

        if (itNew == null) {
            Response.status(Status.BAD_REQUEST).build();

        }

        ItineraryStatusRepresentation is = new ItineraryStatusRepresentation(itNew);

        // set up links

        return Response.ok(is).build();

    }

    @DELETE
    public Response cancelPlanning(@PathParam("cid") String cid,
            @PathParam("iid") String iid) {

        try {
            boolean deleted =
                    itService.deleteItinerary(cid, iid);

            if (deleted) {

                // set up links

                return Response.ok(new ItineraryStatusRepresentation(null)).build();

            } else {
                return Response.status(Status.NOT_FOUND).build();
            }

        } catch (InvalidStatusException e) {
            // cannot cancel - status is not PLANNING
            return Response.status(Status.BAD_REQUEST).build();

        }

    }

    @PUT
    @Path("booking")
    public Response bookItinerary(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            CreditCardInfo ccInfo) {

        try {
            boolean booked = itService.bookItinerary(cid, iid,
                    ccInfo);

            if (!booked) {
                // cannot book (invalid status)
                return Response.status(Status.BAD_REQUEST).build();
            }

            // set up links

            return Response.ok(new ItineraryStatusStatusRepresentation(
                    ws.travelgood.domain.ItineraryStatus.BOOKED)).build();

        } catch (BookingException e) {
            // booking failed, exception contains all the info

            return Response.status(502).entity(e.getMessage()).type(
                    MediaType.TEXT_PLAIN).build();
        }

    }

    @GET
    @Path("status")
    @Produces("application/xml")
    public Response getStatus(@PathParam("cid") String cid,
            @PathParam("iid") String iid) {

        Itinerary it = itService.getItinerary(cid, iid);

        if (it == null) {
            return Response.status(Status.NOT_FOUND).build();

        }

        ItineraryStatusStatusRepresentation iss = new ItineraryStatusStatusRepresentation(it.getCurrentStatus());

        // set up links

        return Response.ok(iss).build();

    }

    @PUT
    @Path("status")
    public Response cancelItinerary(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            CreditCardInfo ccInfo) {

        try {
            boolean cancelled = itService.cancelItinerary(
                    cid, iid, ccInfo);

            if (!cancelled) {
                return Response.status(Status.BAD_REQUEST).build();
            }

            // set up links

            return Response.ok(new ItineraryStatusRepresentation(null)).build();

        } catch (NullPointerException e) {
            return Response.status(Status.NOT_FOUND).build();

        } catch (BookingException e) {
            // cancel failed
            return Response.status(502).entity(e.getMessage()).type(
                    MediaType.TEXT_PLAIN).build();

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
