/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.hotels;

import ws.travelgood.domain.hotel.HotelList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import ws.niceview.types.HotelListType;

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
    public HotelList getXml(@QueryParam("dateFrom") String dateFromStr,
            @QueryParam("dateTo") String dateToStr,
            @QueryParam("city") String city) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date dateFrom;
        Date dateTo;
        try {
            dateFrom = df.parse(dateFromStr);
            dateTo = df.parse(dateToStr);

        } catch (ParseException ex) {
            Logger.getLogger(HotelsResource.class.getName()).log(Level.SEVERE,
                    null, ex);
            return new HotelList(null);
        }


        ws.niceview.NiceViewWSDLService service =
                new ws.niceview.NiceViewWSDLService();
        ws.niceview.NiceViewWSDLPortType port = service.getNiceViewWSDLPort();

        Calendar dateFromCal = Calendar.getInstance();
        dateFromCal.setTime(dateFrom);
        java.util.Calendar departureDate = dateFromCal;

        Calendar dateToCal = Calendar.getInstance();
        dateToCal.setTime(dateFrom);
        java.util.Calendar arrivalDate = dateToCal;


        HotelListType result = port.getHotels(city, departureDate, arrivalDate);

        return new HotelList(result.getHotel());

    }

    @Path("{bookingNumber}")
    public HotelResource getHotelResource() {
        return new HotelResource();
    }
}
