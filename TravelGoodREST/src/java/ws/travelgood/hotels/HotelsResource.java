/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.hotels;

import javax.xml.datatype.DatatypeConfigurationException;
import ws.travelgood.types.hotel.HotelList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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

        GregorianCalendar dateFromGCal = new GregorianCalendar();
        dateFromGCal.setTime(dateFrom);

        GregorianCalendar dateToGCal = new GregorianCalendar();
        dateToGCal.setTime(dateFrom);

        XMLGregorianCalendar departureDate;
        XMLGregorianCalendar arrivalDate;
        
        try {
            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(dateFromGCal);
            
            arrivalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateToGCal);
            
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }

        HotelListType result = port.getHotels(city, departureDate, arrivalDate);

        return new HotelList(result.getHotel());

    }

    @Path("{bookingNumber}")
    public HotelResource getHotelResource() {
        return new HotelResource();
    }
}
