/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.hotels;

import ws.travelgood.types.hotel.HotelList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import ws.travelgood.ItinerariesResource;

/**
 *
 * @author mkucharek
 */
public class HotelsResource {

    @Context
    private UriInfo context;

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

        HotelList hList = ItinerariesResource.itineraryManager.getHotels(dateFrom,
                dateTo, city);

        return Response.ok(hList).build();

    }

    @Path("{bookingNumber}")
    public HotelResource getHotelResource() {
        return new HotelResource();
    }
}
