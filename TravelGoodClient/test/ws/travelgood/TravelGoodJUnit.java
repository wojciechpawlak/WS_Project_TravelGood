/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author s120997
 */
public class TravelGoodJUnit {

    @Test
    public void testCreateItinerary() {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        int customerId = 3;
        int itineraryId = 3;
        // TODO process result here
        java.lang.String result = port.createItinerary(customerId, itineraryId);

        assertEquals("Itinerary Created", result);

        java.lang.String city = "Barcelona";
        java.util.Calendar arrivalDate = Calendar.getInstance();
        arrivalDate.set(2012, 11, 23);
        java.util.Calendar departureDate = Calendar.getInstance();
        departureDate.set(2012, 11, 25);

        // TODO process result here
        ws.niceview.types.HotelListType result2 = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        assert(result2.getHotel().isEmpty());

    }

    /*@Test
    public void testGetHotel() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        java.lang.String city = "Barcelona";
        java.util.Calendar arrivalDate = Calendar.getInstance();
        arrivalDate.set(2012, 11, 23);
        java.util.Calendar departureDate = Calendar.getInstance();
        departureDate.set(2012, 11, 25);
        
        int customerId = 2;
        int itineraryId = 2;
        // TODO process result here
        ws.niceview.types.HotelListType result = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        assert(result.getHotel().isEmpty());
        

    }*/
}
