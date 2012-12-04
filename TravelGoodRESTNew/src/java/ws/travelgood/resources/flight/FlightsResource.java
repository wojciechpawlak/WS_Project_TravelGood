/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources.flight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.service.FlightService;
import ws.travelgood.service.impl.FlightBookingServiceImpl;
import ws.travelgood.statuses.FlightStatusRepresentation;
import ws.travelgood.statuses.FlightsStatusRepresentation;

/**
 *
 * @author mkucharek
 * @author kbarre
 */
public class FlightsResource {

    @Context
    private UriInfo context;

    private FlightService fbService = FlightBookingServiceImpl.getInstance();

    public FlightsResource() {
    }

    @GET
    @Produces("application/xml")
    public Response getFlights(@QueryParam("cityFrom") String cityFrom,
            @QueryParam("cityTo") String cityTo,
            @QueryParam("date") String dateStr) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date;

        try {
            date = df.parse(dateStr);

        } catch (ParseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).
                    build();

        } catch (NullPointerException e) {
            return Response.status(Status.BAD_REQUEST).build();

        }

        List<FlightBooking> fbList = fbService.getAvailableBookings(date, cityFrom, cityTo);

        FlightsStatusRepresentation fs = new FlightsStatusRepresentation(fbList);

        // set up links

        return Response.ok(fs).build();

    }

    @POST
    @Consumes("application/xml")
    public Response addFlight(@PathParam("cid") String cid,
            @PathParam("iid") String iid, FlightBooking flightBooking) {

        FlightBooking fb = fbService.addBooking(cid, iid, flightBooking);

        if (fb == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        FlightStatusRepresentation fs = new FlightStatusRepresentation(fb);

        // set up links

        return Response.status(Status.CREATED).entity(fs).build();


    }

    @Path("{bid}")
    public FlightResource getFlightResource() {
        return new FlightResource();
    }
}
