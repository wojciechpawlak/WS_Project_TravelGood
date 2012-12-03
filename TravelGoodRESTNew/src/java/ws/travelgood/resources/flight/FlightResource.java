/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.resources.flight;

import java.net.URI;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.resources.ItinerariesResource;
import ws.travelgood.service.FlightService;
import ws.travelgood.service.impl.FlightBookingServiceImpl;

/**
 *
 * @author mkucharek
 */
public class FlightResource {

    @Context
    private UriInfo uriInfo;

    private FlightService fbService = FlightBookingServiceImpl.getInstance();

    public FlightResource() {
    
    }

    @GET
    public FlightBooking getHotel(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            @PathParam("bid") String bid) {

        return fbService.getBooking(cid, iid, bid);
    }

    @DELETE
    public Response deleteHotel(@PathParam("cid") String cid, @PathParam("iid") String id, @PathParam(
            "bid") String bid) {

        boolean deleted = fbService.deleteBooking(cid, id, bid);

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
