/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources.hotel;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.booking.HotelBooking;
import ws.travelgood.service.HotelService;
import ws.travelgood.service.impl.HotelBookingServiceImpl;

/**
 *
 * @author mkucharek
 */
public class HotelResource {

    @Context
    private UriInfo uriInfo;

    private HotelService hbService = HotelBookingServiceImpl.getInstance();

    public HotelResource() {
    }

    @GET
    public HotelBooking getHotel(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            @PathParam("bid") String bid) {

        return hbService.getBooking(cid, iid, bid);
    }

    @DELETE
    public Response deleteHotel(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            @PathParam("bid") String bid) {

        boolean deleted = hbService.deleteBooking(cid, iid, bid);

        if (!deleted) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.ok().build();

    }
}
