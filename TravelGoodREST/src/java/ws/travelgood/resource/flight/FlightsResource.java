/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.resource.flight;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.resource.ItinerariesResource;
import ws.travelgood.service.TravelGoodService;
import ws.travelgood.types.flight.FlightBooking;

/**
 *
 * @author mkucharek
 */
public class FlightsResource {

    @Context
    private UriInfo context;

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
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();

        } catch (NullPointerException e) {
            return Response.status(Status.BAD_REQUEST).build();

        }

        List<FlightBooking> fbList = TravelGoodService.getInstance().getAvailableFlightBookings(date,
                cityFrom, cityTo);

        // nasty hack - we need to return an array, because returning a list throws errors..
        // probably because of old jersey library..
        return Response.ok(fbList.toArray(new FlightBooking[fbList.size()])).build();

    }

    @POST
    public Response addFlight(@PathParam("itineraryId") Integer id, FlightBooking flightBooking) {

        Integer bookingId = TravelGoodService.getInstance().addFlight(id, flightBooking);

        URI uri = UriBuilder
                .fromPath(bookingId.toString())
                .build();

        // we should return flight here, not itinerary

        return Response.created(uri).build();


    }

    @Path("{bookingId}")
    public FlightResource getFlightResource() {
        return new FlightResource();
    }
    
}
