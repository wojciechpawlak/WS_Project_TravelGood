/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.resources.hotel;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.domain.booking.HotelBooking;
import ws.travelgood.service.HotelService;
import ws.travelgood.service.impl.HotelBookingServiceImpl;

/**
 *
 * @author mkucharek
 */
public class HotelsResource {

    @Context
    private UriInfo context;

    private HotelService hbService = HotelBookingServiceImpl.getInstance();

    public HotelsResource() {

    }
    
    @GET
    @Produces("application/xml")
    public Response getHotels(@QueryParam("dateFrom") String dateFromStr,
            @QueryParam("dateTo") String dateToStr,
            @QueryParam("city") String city) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateFrom;
        Date dateTo;
        
        try {
            dateFrom = df.parse(dateFromStr);
            dateTo = df.parse(dateToStr);

        } catch (ParseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();

        } catch (NullPointerException e) {
            return Response.status(Status.BAD_REQUEST).build();

        }

        List<HotelBooking> hbList = hbService.getAvailableBookings(dateFrom, dateTo, city);

        // nasty hack - we need to return an array, because returning a list throws errors..
        // probably because of old jersey library..
        return Response.ok(hbList.toArray(new HotelBooking[hbList.size()])).build();

    }

    @POST
    @Consumes("application/xml")
    public Response addHotel(@PathParam("cid") String cid,
            @PathParam("iid") String iid,
            HotelBooking hotelBooking) {

        HotelBooking hb = hbService.addBooking(cid, iid, hotelBooking);

        URI uri = UriBuilder
                .fromPath(hb.getId())
                .build();

        // we should return hotel here, not itinerary

        return Response.created(uri).build();

    }

    @Path("{bid}")
    public HotelResource getHotelResource() {
        return new HotelResource();
    }

}
