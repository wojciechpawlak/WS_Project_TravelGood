/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.hotels;

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
public class HotelResource {

    @Context
    private UriInfo uriInfo;

    public HotelResource() {
    }

    @PUT
    public Response addHotel(@PathParam("id") String id, @PathParam(
            "bookingNumber") String bookingNumber) {

        boolean added = ItinerariesResource.itineraryDAO.addHotel(Integer.
                parseInt(id), bookingNumber);

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
    public Response deleteHotel(@PathParam("id") String id, @PathParam(
            "bookingNumber") String bookingNumber) {

        boolean deleted = ItinerariesResource.itineraryDAO.deleteHotel(Integer.
                parseInt(id), bookingNumber);

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
