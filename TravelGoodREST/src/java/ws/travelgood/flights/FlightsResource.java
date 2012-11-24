/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.flights;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.ItinerariesResource;
import ws.travelgood.types.flight.FlightList;

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

        FlightList fList = ItinerariesResource.itineraryManager.getFlights(date,
                cityFrom, cityTo);

        return Response.ok(fList).build();

    }

    @Path("{bookingNumber}")
    public FlightResource getFlightResource() {
        return new FlightResource();
    }
    
}
