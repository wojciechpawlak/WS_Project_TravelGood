/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resource.hotel;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.service.TravelGoodService;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public class HotelResource {

    @Context
    private UriInfo uriInfo;

    public HotelResource() {
    
    }

    @GET
    public HotelBooking getHotel(@PathParam("itineraryId") Integer itineraryId,
            @PathParam("bookingId") Integer bookingId) {

        return TravelGoodService.getInstance().getHotelBooking(itineraryId, bookingId);
    }

    @DELETE
    public Response deleteHotel(@PathParam("itineraryId") Integer itineraryId,
            @PathParam("bookingId") Integer bookingId) {

        boolean deleted = TravelGoodService.getInstance().deleteHotel(
                itineraryId, bookingId);

        if (!deleted) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.ok().build();

    }
}
