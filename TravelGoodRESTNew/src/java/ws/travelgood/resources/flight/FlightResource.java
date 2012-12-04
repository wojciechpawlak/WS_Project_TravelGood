/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.resources.flight;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.service.FlightService;
import ws.travelgood.service.impl.FlightBookingServiceImpl;
import ws.travelgood.statuses.FlightStatusRepresentation;

/**
 *
 * @author mkucharek
 * @author kbarre
 */
public class FlightResource {

    @Context
    private UriInfo uriInfo;

    private FlightService fbService = FlightBookingServiceImpl.getInstance();

    public FlightResource() {
    
    }

    @GET
    public Response getFlight(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            @PathParam("bid") String bid) {

        FlightBooking fb = fbService.getBooking(cid, iid, bid);

        if (fb == null) {
            return Response.status(Status.NOT_FOUND).build();

        }

        FlightStatusRepresentation fs = new FlightStatusRepresentation(fb);

        // set up links

        return Response.ok(fs).build();

    }

    @DELETE
    public Response deleteFlight(@PathParam("cid") String cid, @PathParam("iid") String id, @PathParam(
            "bid") String bid) {

        boolean deleted = fbService.deleteBooking(cid, id, bid);

        if (!deleted) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        FlightStatusRepresentation fs = new FlightStatusRepresentation(null);

        // set up links

        return Response.ok(fs).build();

    }
    
}
