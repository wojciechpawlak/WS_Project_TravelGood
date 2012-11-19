/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import java.util.Calendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import ws.lameduck.types.FlightInformationListType;
import ws.niceview.types.*;
import static org.junit.Assert.*;

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
        int customerId = 1;
        int itineraryId = 1;
        // TODO process result here
        java.lang.String result = port.createItinerary(customerId, itineraryId);

        assertEquals("Itinerary Created", result);

    }

    
    @Test
    public void testGetHotel() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        int customerId = 2;
        int itineraryId = 2;
        // TODO process result here
        port.createItinerary(customerId, itineraryId);

        java.lang.String city = "Barcelona";

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-11-23");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-11-25");

        /*java.util.Calendar arrivalDate = Calendar.getInstance();
        arrivalDate.set(2012, 11, 23);
        java.util.Calendar departureDate = Calendar.getInstance();
        departureDate.set(2012, 11, 25);*/

        ws.niceview.types.HotelListType result = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        assertFalse(result.getHotel().isEmpty());
        
    }

    
    @Test
    public void testAddHotel() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        int customerId = 3;
        int itineraryId = 3;
        // TODO process result here
        port.createItinerary(customerId, itineraryId);

        java.lang.String city = "Barcelona";

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-11-23");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-11-25");

        /*java.util.Calendar arrivalDate = Calendar.getInstance();
        arrivalDate.set(2012, 11, 23);
        java.util.Calendar departureDate = Calendar.getInstance();
        departureDate.set(2012, 11, 25);*/

        ws.niceview.types.HotelListType resultGetHotel = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        String result = port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        assertEquals("Hotel added",result);

    }

    @Test
    public void testGetItinerary() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        int customerId = 4;
        int itineraryId = 4;
        // TODO process result here
        port.createItinerary(customerId, itineraryId);

        java.lang.String city = "Barcelona";

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-11-23");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-11-25");

        /*java.util.Calendar arrivalDate = Calendar.getInstance();
        arrivalDate.set(2012, 11, 23);
        java.util.Calendar departureDate = Calendar.getInstance();
        departureDate.set(2012, 11, 25);*/

        ws.niceview.types.HotelListType resultGetHotel = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        //TODO : REMOVE
        HotelType myHotel = new HotelType();
        myHotel.setBookingNumber("H123456");
        myHotel.setHotelName("NiceHotel");
        myHotel.setHotelStayPrice(999999.99);
        myHotel.setIfCreditCardRequired(true);

        AddressType myAddress = new AddressType();
        myAddress.setCity("Copenhagen");
        myAddress.setPostcode("2700");
        myAddress.setStreet("Blablabla");

        myHotel.setHotelAddress(myAddress);

        resultGetHotel.getHotel().add(myHotel);
        //TODO : END REMOVE

        port.addHotel("H123456", customerId, itineraryId);

        ws.travelgood.ItineraryType result = port.getItinerary(customerId, itineraryId);

        BookingType myBooking = result.getBookingsHotel().get(0);

        System.out.println(arrivalDate.toString());

        assertEquals("H123456",myBooking.getBookingNumber());
        assertEquals("unconfirmed",myBooking.getBookingStatus());
        assertEquals(arrivalDate.toString(),result.getItineraryStartDate().toString());

    }

        @Test
        public void testGetFlight() throws DatatypeConfigurationException {

            ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
            ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
            // TODO initialize WS operation arguments here
            int customerId = 5;
            int itineraryId = 5;
            // TODO process result here
            port.createItinerary(customerId, itineraryId);

            DatatypeFactory df = DatatypeFactory.newInstance();
            XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");

            FlightInformationListType result = port.getFlight("Copenhagen", "Bucharest",flightDate, customerId, itineraryId);

            assertFalse(result.getFlightInformation().isEmpty());

        }



}
