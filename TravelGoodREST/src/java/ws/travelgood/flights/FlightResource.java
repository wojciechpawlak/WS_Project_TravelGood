/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.flights;

import java.net.URI;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.ItinerariesResource;

/**
 *
 * @author mkucharek
 */
public class FlightResource {

    @Context
    private UriInfo uriInfo;

    public FlightResource() {
    }

    @PUT
    public Response addFlight(@PathParam("id") Integer id, @PathParam(
            "bookingNumber") String bookingNumber) {

        boolean added = ItinerariesResource.itineraryDAO.addFlight(id, bookingNumber);

        if (!added) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        URI uri = UriBuilder
                .fromResource(ItinerariesResource.class)
                .segment(id.toString())
                .build();

        return Response.temporaryRedirect(uri).build();


    }

    @DELETE
    public Response deleteHotel(@PathParam("id") Integer id, @PathParam(
            "bookingNumber") String bookingNumber) {

        boolean deleted = ItinerariesResource.itineraryDAO.deleteFlight(id, bookingNumber);

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
