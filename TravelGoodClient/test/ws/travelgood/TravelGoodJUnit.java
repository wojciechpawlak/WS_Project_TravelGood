/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.CreditCardInfoWrapperType;
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

    System.out.println("I'm here");

    java.lang.String result = port.createItinerary(customerId, itineraryId);

    System.out.println("I'm here");

    assertEquals("Itinerary Created", result);

    //port.bookingItinerary(customerId, itineraryId);

    }

    /*
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

        //Plan a trip by first planning a flight
        //i.e. getting a list of flights and then adding a flight to the itinerary
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");
        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //then by planning a hotel
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-25");
        HotelListType resultGetHotel = port.getHotel("Vienna", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //another flight
        flightDate = df.newXMLGregorianCalendar("2012-12-25");
        resultGetFlight = port.getFlight("Moscow", "Berlin", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //a third flight
        flightDate = df.newXMLGregorianCalendar("2012-12-29");
        resultGetFlight = port.getFlight("Paris", "Tokyo", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //and finally a hotel
        arrivalDate = df.newXMLGregorianCalendar("2012-12-26");
        departureDate = df.newXMLGregorianCalendar("2012-12-28");
        resultGetHotel = port.getHotel("Barcelona", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //Ask for the itinerary and check that it is correct using JUnit's assert statements
        ItineraryType myItinerary = port.getItinerary(customerId, itineraryId);
        //myItinerary = cleanNull(myItinerary);

        assertEquals(3, myItinerary.getBookingsFlight().size());
        assertEquals(2, myItinerary.getBookingsHotel().size());
        assertEquals(df.newXMLGregorianCalendar("2012-12-22"), myItinerary.getItineraryStartDate());

        //in particular, that the booking status for each item is unconfirmed.
        for (BookingType myBookingFlight : myItinerary.getBookingsFlight()) {
            assertEquals("unconfirmed", myBookingFlight.getBookingStatus());
        }

        for (BookingType myBookingHotel : myItinerary.getBookingsHotel()) {
            assertEquals("unconfirmed", myBookingHotel.getBookingStatus());
        }


        //Book the itinerary and ask again for the itinerary.
        CreditCardInfoWrapperType ccit = new CreditCardInfoWrapperType();
        ccit.setName("Anne Strandberg");
        ccit.setNumber("50408816");
        ExpirationDateType edt = new ExpirationDateType();
        edt.setMonth(5);
        edt.setYear(9);
        ccit.setExpirationDate(edt);
        String result = port.bookingItinerary(customerId, itineraryId, ccit);
        assertEquals("Booking done", result);
        myItinerary = port.getItinerary(customerId, itineraryId);
        //myItinerary = cleanNull(myItinerary);

        //Check that each booking status is now confirmed
        for (BookingType myBookingFlight : myItinerary.getBookingsFlight()) {
            assertEquals("confirmed", myBookingFlight.getBookingStatus());
        }

        for (BookingType myBookingHotel : myItinerary.getBookingsHotel()) {
            assertEquals("confirmed", myBookingHotel.getBookingStatus());
        }


    }

    @Test
    public void testP2() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 2;
        int itineraryId = 2;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //Plan a trip by first planning a flight
        //i.e. getting a list of flights and then adding a flight to the itinerary
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");
        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        ItineraryType myItinerary = port.getItinerary(customerId, itineraryId);

        assertEquals(1, myItinerary.getBookingsFlight().size());
        assertEquals(0, myItinerary.getBookingsHotel().size());
        assertEquals(df.newXMLGregorianCalendar("2012-12-22"), myItinerary.getItineraryStartDate());

        // Cancel planning

        String cancelItineraryString = port.cancelItinerary(customerId, itineraryId);

        assertEquals("Planning canceled", cancelItineraryString);
    }

    @Test
    public void testP3a() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 3;
        int itineraryId = 3;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //Plan a trip by first getting a list of flights
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-29");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-31");

        HotelListType resultGetHotel = port.getHotel("SleepHotel", arrivalDate, departureDate, customerId, itineraryId);

        assertEquals(0, resultGetHotel.getHotel().size());

    }

    @Test
    public void testP3b() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 4;
        int itineraryId = 4;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //Plan a trip by first getting a list of flights
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-29");
        FlightInformationListType resultGetFlight = port.getFlight("SleepCity", "BedCity", flightDate, customerId, itineraryId);

        assertEquals(0, resultGetFlight.getFlightInformation().size());

    }
    /*

    private ItineraryType cleanNull(ItineraryType myItinerary) {

    ItineraryType result = new ItineraryType();

    List<BookingType> resultHotelList = result.getBookingsHotel();
    List<BookingType> resultFlightList = result.getBookingsFlight();

    for(int i = 0; i < myItinerary.getBookingsHotel().size(); i++)
    if (!(myItinerary.getBookingsHotel().get(i).getBookingNumber() == null
    && myItinerary.getBookingsHotel().get(i).getBookingStatus() == null))
    {
    resultHotelList.add(myItinerary.getBookingsHotel().get(i));
    }

    for(int i = 0; i < myItinerary.getBookingsFlight().size(); i++)
    if (!(myItinerary.getBookingsFlight().get(i).getBookingNumber() == null
    && myItinerary.getBookingsFlight().get(i).getBookingStatus() == null))
    {
    resultFlightList.add(myItinerary.getBookingsFlight().get(i));
    }

    result.setItineraryStartDate(myItinerary.getItineraryStartDate());

    return result;

    }
     */
}
