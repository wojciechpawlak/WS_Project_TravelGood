/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.resource.flight;

import java.net.URI;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
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
public class FlightResource {

    @Context
    private UriInfo uriInfo;

    public FlightResource() {
    
    }

    @GET
    public FlightBooking getHotel(@PathParam("itineraryId") Integer itineraryId,
            @PathParam("bookingId") Integer bookingId) {

        return TravelGoodService.getInstance().getFlightBooking(itineraryId, bookingId);
    }

    @DELETE
    public Response deleteHotel(@PathParam("itineraryId") Integer id, @PathParam(
            "bookingId") Integer bookingId) {

        boolean deleted = TravelGoodService.getInstance().deleteFlight(id, bookingId);

        if (!deleted) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        URI uri = UriBuilder
                .fromResource(ItinerariesResource.class)
                .segment(id.toString())
                .build();

        return Response.temporaryRedirect(uri).build();

    }
    
}
