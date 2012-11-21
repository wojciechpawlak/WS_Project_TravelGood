/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

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

    /*
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

        port.bookingItinerary(customerId, itineraryId);

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

        //java.util.Calendar arrivalDate = Calendar.getInstance();
        //arrivalDate.set(2012, 11, 23);
        //java.util.Calendar departureDate = Calendar.getInstance();
        //departureDate.set(2012, 11, 25);

        ws.niceview.types.HotelListType result = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        assertFalse(result.getHotel().isEmpty());

        port.bookingItinerary(customerId, itineraryId);
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

        //java.util.Calendar arrivalDate = Calendar.getInstance();
        //arrivalDate.set(2012, 11, 23);
        //java.util.Calendar departureDate = Calendar.getInstance();
        //departureDate.set(2012, 11, 25);

        ws.niceview.types.HotelListType resultGetHotel = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);

        String result = port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        assertEquals("Hotel added", result);

        port.bookingItinerary(customerId, itineraryId);

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

        FlightInformationListType result = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);

        assertFalse(result.getFlightInformation().isEmpty());

        port.bookingItinerary(customerId, itineraryId);

    }

    @Test
    public void testAddFlight() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        int customerId = 6;
        int itineraryId = 6;
        // TODO process result here
        port.createItinerary(customerId, itineraryId);

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");

        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);

        String result = port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);

        assertEquals("Flight added", result);

        port.bookingItinerary(customerId, itineraryId);

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

        //java.util.Calendar arrivalDate = Calendar.getInstance();
        //arrivalDate.set(2012, 11, 23);
        //java.util.Calendar departureDate = Calendar.getInstance();
        //departureDate.set(2012, 11, 25);

        ws.niceview.types.HotelListType resultGetHotel = port.getHotel(city, arrivalDate, departureDate, customerId, itineraryId);
        port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");
        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);
        port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);

        ws.travelgood.ItineraryType result = port.getItinerary(customerId, itineraryId);

        BookingType myAddedHotel = result.getBookingsHotel().get(0);
        BookingType myAddedFlight = result.getBookingsFlight().get(0);

        assertEquals("1", myAddedHotel.getBookingNumber());
        assertEquals("unconfirmed", myAddedHotel.getBookingStatus());

        assertEquals("54321B", myAddedFlight.getBookingNumber());
        assertEquals("unconfirmed", myAddedFlight.getBookingStatus());

        assertEquals(flightDate.toString(), result.getItineraryStartDate().toString());

        port.bookingItinerary(customerId, itineraryId);

    }*/

    @Test
    public void testP1() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 1;
        int itineraryId = 1;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //TODO : REMOVE

        ItineraryType myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        DatatypeFactory df1 = DatatypeFactory.newInstance();

        XMLGregorianCalendar arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-22");
        XMLGregorianCalendar departureDate1 = df1.newXMLGregorianCalendar("2012-12-25");
        HotelListType resultGetHotel1 = port.getHotel("Vienna", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-26");
        departureDate1 = df1.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel1 = port.getHotel("Barcelona", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-26");
        departureDate1 = df1.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel1 = port.getHotel("Vienna", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-26");
        departureDate1 = df1.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel1 = port.getHotel("Vienna", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-26");
        departureDate1 = df1.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel1 = port.getHotel("Vienna", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());

        arrivalDate1 = df1.newXMLGregorianCalendar("2012-12-26");
        departureDate1 = df1.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel1 = port.getHotel("Vienna", arrivalDate1, departureDate1, customerId, itineraryId);
        if (resultGetHotel1.getHotel().size() > 0)
            port.addHotel(resultGetHotel1.getHotel().get(0).getBookingNumber(), customerId, itineraryId);

        myItinerary2 = port.getItinerary(customerId, itineraryId);
        System.out.println(myItinerary2.getBookingsHotel().size());
        
        System.out.println();

        for(BookingType myBooking : myItinerary2.getBookingsHotel())
            System.out.println(myBooking.getBookingNumber());

        port.bookingItinerary(customerId, itineraryId);
        
        //TODO : END REMOVE

        /*
        //Plan a trip by first planning a flight
        //i.e. getting a list of flights and then adding a flight to the itinerary
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");
        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0)
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);

        //then by planning a hotel
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-25");
        HotelListType resultGetHotel = port.getHotel("Vienna", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0)
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        
        //another flight
        flightDate = df.newXMLGregorianCalendar("2012-12-25");
        resultGetFlight = port.getFlight("Berlin", "Moscow", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0)
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);

        //a third flight
        flightDate = df.newXMLGregorianCalendar("2012-12-29");
        resultGetFlight = port.getFlight("Paris", "Tokyo", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0)
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);

        //and finally a hotel
        arrivalDate = df.newXMLGregorianCalendar("2012-12-26");
        departureDate = df.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel = port.getHotel("Barcelona", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0)
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        
        //Ask for the itinerary and check that it is correct using JUnit's assert statements
        ItineraryType myItinerary = port.getItinerary(customerId, itineraryId);

        assertEquals(0,myItinerary.getBookingsFlight().size()); //TODO : Replace by 3
        assertEquals(3,myItinerary.getBookingsHotel().size()); //TODO : Replace by 2
        //TODO : Make this work //assertEquals(df.newXMLGregorianCalendar("2012-12-22"),myItinerary.getItineraryStartDate());

        System.out.println(myItinerary.getBookingsHotel().get(0).getBookingNumber());
        System.out.println(myItinerary.getBookingsHotel().get(1).getBookingNumber());
        System.out.println(myItinerary.getBookingsHotel().get(2).getBookingNumber());

        //in particular, that the booking status for each item is unconfirmed.
        for(BookingType myBookingFlight : myItinerary.getBookingsFlight())
            assertEquals("unconfirmed",myBookingFlight.getBookingStatus());

        for(BookingType myBookingHotel : myItinerary.getBookingsHotel())
            assertEquals("unconfirmed",myBookingHotel.getBookingStatus());

        //Book the itinerary and ask again for the itinerary.
        port.bookingItinerary(customerId, itineraryId);
        //myItinerary = port.getItinerary(customerId, itineraryId);

        //Check that each booking status is now confirmed
        for(BookingType myBookingFlight : myItinerary.getBookingsFlight())
            assertEquals("confirmed",myBookingFlight.getBookingStatus());

        for(BookingType myBookingHotel : myItinerary.getBookingsHotel())
            assertEquals("confirmed",myBookingHotel.getBookingStatus());
        */
    }
}
