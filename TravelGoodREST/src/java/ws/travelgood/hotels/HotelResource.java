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
import ws.travelgood.manager.impl.TravelGoodManager;

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
    public Response addHotel(@PathParam("id") Integer id, @PathParam(
            "bookingNumber") String bookingNumber) {

        boolean added = TravelGoodManager.getInstance().addHotel(id, bookingNumber);

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

        boolean deleted = TravelGoodManager.getInstance().deleteHotel(id, bookingNumber);

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
