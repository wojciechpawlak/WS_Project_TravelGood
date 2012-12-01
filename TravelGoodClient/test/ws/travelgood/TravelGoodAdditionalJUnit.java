/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood;

import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import ws.lameduck.types.CreditCardInfoWrapperType;
import ws.lameduck.types.FlightInformationListType;
import ws.niceview.types.HotelListType;
import static org.junit.Assert.*;

/**
 *
 * @author Wojtek
 */
public class TravelGoodAdditionalJUnit {

    @Test
    public void testP3a() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 3;
        int itineraryId = 3;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //Plan a trip by first getting a list of hotels
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-29");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-31");

        //The getHotels operation to the hotel reservations service does not answer within 5 seconds
        HotelListType resultGetHotel = port.getHotel("SleepCity", arrivalDate, departureDate, customerId, itineraryId);

        //Check that the resulting list of hotels is empty.
        assertEquals(0, resultGetHotel.getHotel().size());

//        resultGetHotel = port.getHotel("Vienna", arrivalDate, departureDate, customerId, itineraryId);
//        assertFalse(resultGetHotel.getHotel().isEmpty());
//        assertEquals("Vienna",resultGetHotel.getHotel().get(0).getHotelAddress().getCity());
//
//        //The getHotels operation to the hotel reservations service does not answer within 5 seconds
//        resultGetHotel = port.getHotel("SleepCity", arrivalDate, departureDate, customerId, itineraryId);
//
//        //Check that the resulting list of hotels is empty.
//        assertEquals(0, resultGetHotel.getHotel().size());

        //Cancel planning TODO
        port.cancelItinerary(customerId, itineraryId);

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

        //The getFlights operation to the airline reservation service does not answer within 5 seconds.
        FlightInformationListType resultGetFlight = port.getFlight("SleepCity", "BedCity", flightDate, customerId, itineraryId);

        //Check that the resulting list of flights is empty
        assertEquals(0, resultGetFlight.getFlightInformation().size());

        //Cancel planning TODO
        port.cancelItinerary(customerId, itineraryId);
    }

    @Test
    public void testB2() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 8;
        int itineraryId = 8;

        //Create the itinerary
        port.createItinerary(customerId, itineraryId);

        //Plan an itinerary with three bookings.
        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar flightDate = df.newXMLGregorianCalendar("2012-12-22");
        FlightInformationListType resultGetFlight = port.getFlight("Copenhagen", "Bucharest", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        flightDate = df.newXMLGregorianCalendar("2012-12-25");
        resultGetFlight = port.getFlight("Moscow", "Berlin", flightDate, customerId, itineraryId);
        if (resultGetFlight.getFlightInformation().size() > 0) {
            port.addFlight(resultGetFlight.getFlightInformation().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //then by planning a hotel
        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-25");
        HotelListType resultGetHotel = port.getHotel("Zgierz", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        resultGetHotel = port.getHotel("Barcelona", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        //Get the itinerary and make sure that the booking status is unconfirmed for each entry.
        ItineraryType myItinerary = port.getItinerary(customerId, itineraryId);

        for (BookingType myBookingFlight : myItinerary.getBookingsFlight()) {
            assertEquals("unconfirmed", myBookingFlight.getBookingStatus());
        }

        for (BookingType myBookingHotel : myItinerary.getBookingsHotel()) {
            assertEquals("unconfirmed", myBookingHotel.getBookingStatus());
        }

        //Then book the itinerary. During booking, the second booking should fail
        CreditCardInfoWrapperType ccit = new CreditCardInfoWrapperType();
        ccit.setName("Bech Camilla"); //Only have a limit of 1000
        ccit.setNumber("50408822");
        ExpirationDateType edt = new ExpirationDateType();
        edt.setMonth(7);
        edt.setYear(9);
        ccit.setExpirationDate(edt);
        String resultBooking = port.bookingItinerary(customerId, itineraryId, ccit);

        //Get the itinerary and check that the result of the bookTrip operation records a failure
        assertEquals("Booking failure", resultBooking);
        myItinerary = port.getItinerary(customerId, itineraryId);

        //and that the returned itinerary has cancelled as the booking status of the first booking
        for (BookingType myBookingFlight : myItinerary.getBookingsFlight()) {
            assertEquals("cancelled", myBookingFlight.getBookingStatus());
        }

        //and unconfirmed for the status of the second and third booking.
        assertEquals("cancelled", myItinerary.getBookingsHotel().get(0).getBookingStatus());
        assertEquals("unconfirmed", myItinerary.getBookingsHotel().get(1).getBookingStatus());

        //Cancel the planning
        port.cancelItinerary(customerId, itineraryId);
    }

    @Test
    public void testC3() throws DatatypeConfigurationException {

        ws.travelgood.TravelGoodWSDLService service = new ws.travelgood.TravelGoodWSDLService();
        ws.travelgood.TravelGoodWSDLPortType port = service.getTravelGoodWSDLPortTypeBindingPort();

        //ID of the customer and itinerary
        int customerId = 9;
        int itineraryId = 9;

        port.createItinerary(customerId, itineraryId);

        //Create an itinerary with three bookings and book it.
        DatatypeFactory df = DatatypeFactory.newInstance();

        XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2012-12-25");
        HotelListType resultGetHotel = port.getHotel("Barcelona", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        departureDate = df.newXMLGregorianCalendar("2012-12-25");
        resultGetHotel = port.getHotel("CancelCity", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        arrivalDate = df.newXMLGregorianCalendar("2012-12-22");
        departureDate = df.newXMLGregorianCalendar("2012-12-25");
        resultGetHotel = port.getHotel("Vienna", arrivalDate, departureDate, customerId, itineraryId);
        if (resultGetHotel.getHotel().size() > 0) {
            port.addHotel(resultGetHotel.getHotel().get(0).getBookingNumber(), customerId, itineraryId);
        }

        CreditCardInfoWrapperType ccit = new CreditCardInfoWrapperType();
        ccit.setName("Anne Strandberg");
        ccit.setNumber("50408816");
        ExpirationDateType edt = new ExpirationDateType();
        edt.setMonth(5);
        edt.setYear(9);
        ccit.setExpirationDate(edt);
        port.bookingItinerary(customerId, itineraryId, ccit);

        //Make sure that the booking status is confirmed for each entry.
        ItineraryType myItinerary = port.getItinerary(customerId, itineraryId);

        for (BookingType myBookingFlight : myItinerary.getBookingsFlight()) {
            assertEquals("confirmed", myBookingFlight.getBookingStatus());
        }

        for (BookingType myBookingHotel : myItinerary.getBookingsHotel()) {
            assertEquals("confirmed", myBookingHotel.getBookingStatus());
        }

        //During cancelling of the trip, the cancellation of the second booking should fail.
        String resultCancel = port.cancelItinerary(customerId, itineraryId);

        //Check that the cancelling resulted in an error condition (e.g. value of status variable, exception, HTTP status code).
        assertEquals("Cancel failed. Some of the bookings were not cancelled", resultCancel); // TODO: Not done. Must simulate for error in cancelling. This should work in final version

        //Get the itinerary and check that the returned itinerary has cancelled as the first and third booking and confirmed for the second booking.
        myItinerary = port.getItinerary(customerId, itineraryId);

        assertEquals("cancelled", myItinerary.getBookingsHotel().get(0).getBookingStatus());
        assertEquals("confirmed", myItinerary.getBookingsHotel().get(1).getBookingStatus());
        assertEquals("cancelled", myItinerary.getBookingsHotel().get(2).getBookingStatus());

    }

//    private ItineraryType cleanNull(ItineraryType myItinerary) {
//
//        ItineraryType result = new ItineraryType();
//
//        List<BookingType> resultHotelList = result.getBookingsHotel();
//        List<BookingType> resultFlightList = result.getBookingsFlight();
//
//        for (int i = 0; i < myItinerary.getBookingsHotel().size(); i++) {
//            if (!(myItinerary.getBookingsHotel().get(i).getBookingNumber() == null && myItinerary.getBookingsHotel().get(i).getBookingStatus() == null)) {
//                resultHotelList.add(myItinerary.getBookingsHotel().get(i));
//            }
//        }
//
//        for (int i = 0; i < myItinerary.getBookingsFlight().size(); i++) {
//            if (!(myItinerary.getBookingsFlight().get(i).getBookingNumber() == null && myItinerary.getBookingsFlight().get(i).getBookingStatus() == null)) {
//                resultFlightList.add(myItinerary.getBookingsFlight().get(i));
//            }
//        }
//
//        result.setItineraryStartDate(myItinerary.getItineraryStartDate());
//
//        return result;
//    }
}
