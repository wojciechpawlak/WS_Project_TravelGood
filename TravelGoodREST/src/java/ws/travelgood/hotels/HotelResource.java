/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.hotels;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.ItinerariesResource;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;
import ws.travelgood.types.hotel.HotelBooking;
import ws.travelgood.exceptions.InvalidStatusException;

/**
 *
 * @author mkucharek
 */
public class HotelResource {

    @Context
    private UriInfo context;

    public HotelResource() {
    }

    @POST
    public Response addHotel(@PathParam("id") String id, @PathParam(
            "bookingNumber") String bookingNumber) {

        Itinerary it = ItinerariesResource.itineraryList.get(
                Integer.parseInt(id));

        if (it.getCurrentStatus() != ItineraryStatus.PLANNING) {
            throw new InvalidStatusException(
                    "Cannot add hotel - " + it +
                    "is no longer in planning state");
        }

        ItinerariesResource.itineraryList.get(Integer.parseInt(id)).
                getHotelBookingList().add(new HotelBooking(bookingNumber));

        return Response.ok().build();

    }

    @DELETE
    public Response deleteHotel(@PathParam("id") String id, @PathParam(
            "bookingNumber") String bookingNumber) {

        HotelBooking toRemove = null;
        for (HotelBooking hb : ItinerariesResource.itineraryList.get(Integer.
                parseInt(id)).getHotelBookingList()) {
            if (hb.getBookingNumber().equals(bookingNumber)) {
                toRemove = hb;
                break;

            }
        }

        if (toRemove != null) {
            ItinerariesResource.itineraryList.get(Integer.parseInt(id)).
                    getHotelBookingList().remove(toRemove);

            return Response.noContent().build();

        }

        return Response.status(Status.NOT_FOUND).build();

    }
}
