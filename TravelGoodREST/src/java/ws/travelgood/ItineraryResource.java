/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.net.URI;
import ws.travelgood.types.Itinerary;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import ws.travelgood.flights.FlightsResource;
import ws.travelgood.hotels.HotelsResource;
import ws.travelgood.manager.BookingException;
import ws.travelgood.manager.impl.TravelGoodManager;
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
    public Response getItinerary(@PathParam("id") Integer itineraryId) {

        Itinerary it =
                TravelGoodManager.getInstance().getItinerary(itineraryId);

        return Response.ok(it).build();

    }

    @GET
    @Path("ccinfo")
    @Produces("application/xml")
    public CreditCardInfo getInfo() {

        CreditCardInfo ccinfo = new CreditCardInfo();

        ccinfo.setName("TestName");

        ExpirationDateType exp = new ExpirationDateType();
        exp.setMonth(11);
        exp.setYear(2014);
        ccinfo.setExpirationDate(exp);

        ccinfo.setNumber("12345");

        return ccinfo;
    }

    @POST
    @Path("book")
    public Response bookItinerary(@PathParam("id") String id, CreditCardInfo ccInfo) {

        try {
            boolean booked = TravelGoodManager.getInstance().book(id, ccInfo);

            if (!booked) {
                return Response.status(Status.BAD_REQUEST).build();
            }

            URI uri = UriBuilder
                    .fromResource(ItinerariesResource.class)
                    .segment(id)
                    .build();

            return Response.temporaryRedirect(uri).build();

        } catch (BookingException e) {
            // booking failed, exception contains all the info

            return Response
                    .status(502)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

    }

    @POST
    @Path("cancel")
    public Response cancelItinerary(@PathParam("id") String id) {

        try {
            boolean cancelled = TravelGoodManager.getInstance().cancel(id);

            if (!cancelled) {
                return Response.status(Status.BAD_REQUEST).build();
            }

            URI uri = UriBuilder.fromResource(ItinerariesResource.class).segment(id).build();

            return Response.temporaryRedirect(uri).build();

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
