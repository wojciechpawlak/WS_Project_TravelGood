/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.lameduck.BookFlightFault;
import ws.lameduck.CancelFlightFault;
import ws.lameduck.LameDuckWSDLPortType;
import ws.lameduck.LameDuckWSDLService;
import ws.lameduck.types.CreditCardInfoWrapperType;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.FlightInformationType;
import ws.lameduck.types.RequestBookFlightType;
import ws.lameduck.types.RequestCancelFlightType;
import ws.lameduck.types.RequestGetFlightType;
import ws.travelgood.domain.FlightBookingEntity;
import ws.travelgood.types.banking.CreditCardInfo;

/**
 *
 * @author mkucharek
 */
public class LameDuckManager implements FlightManager {

    private LameDuckWSDLPortType port;

    public LameDuckManager() {
        port = new LameDuckWSDLService().getLameDuckWSDLPortTypeBindingPort();
    }

    public List<FlightBookingEntity> getFlights(Date date, String cityFromName,
            String cityToName) {

        XMLGregorianCalendar departureDate;

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(df.format(date));

        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }

        RequestGetFlightType input = new RequestGetFlightType();

        input.setFlightDate(departureDate);
        input.setFlightStart(cityFromName);
        input.setFlightDestination(cityToName);

        FlightInformationListType result = port.getFlights(input);

        List<FlightBookingEntity> fbeList = new ArrayList<FlightBookingEntity>();

        for (FlightInformationType fit : result.getFlightInformation()) {
            fbeList.add(toFlightBookingEntity(fit));

        }

        return fbeList;

    }

    public boolean bookFlight(String bookingNumber, CreditCardInfo ccInfo) throws
            BookingException {

        try {
            RequestBookFlightType input = new RequestBookFlightType();
            input.setBookingNumber(bookingNumber);

            CreditCardInfoWrapperType ccit = new CreditCardInfoWrapperType();
            ccit.setName(ccInfo.getName());
            ccit.setNumber(ccInfo.getNumber());
            
            ExpirationDateType edt = new ExpirationDateType();
            edt.setMonth(ccInfo.getExpirationDate().getMonth());
            edt.setYear(ccInfo.getExpirationDate().getYear());
            ccit.setExpirationDate(edt);

            input.setCreditCardInformation(ccit);

            return port.bookFlights(input);

        } catch (BookFlightFault e) {
            // booking failed due to wrong credit card info
            throw new BookingException("Flight Booking [bookingNumber=" +
                    bookingNumber + "] failed", e);
            
        }

    }

    public boolean cancelFlight(String bookingNumber, double price, CreditCardInfo ccInfo) throws BookingException {

        try {
            
            RequestCancelFlightType input = new RequestCancelFlightType();
            input.setBookingNumber(bookingNumber);
            input.setPrice(price);

            CreditCardInfoWrapperType ccInfoWrapper = new CreditCardInfoWrapperType();
            ccInfoWrapper.setName(ccInfo.getName());
            ccInfoWrapper.setNumber(ccInfo.getNumber());

            ExpirationDateType edt = new ExpirationDateType();
            edt.setMonth(ccInfo.getExpirationDate().getMonth());
            edt.setYear(ccInfo.getExpirationDate().getYear());

            ccInfoWrapper.setExpirationDate(edt);

            input.setCreditCardInformation(ccInfoWrapper);

            port.cancelFlight(input);
            return true;

        } catch (CancelFlightFault e) {
            throw new BookingException("Cancel failed for HotelBooking[bookingNumber=" +
                    bookingNumber + "]", e);

        }

    }

    private FlightBookingEntity toFlightBookingEntity(FlightInformationType fit) {
        
        return new FlightBookingEntity(null,
                fit.getBookingNumber(),
                fit.getPrice(),
                fit.getAirlineReservationServiceName(),
                fit.getFlight().getStartAirport(),
                fit.getFlight().getDestinationAirport(),
                fit.getFlight().getLiftOffDate().toGregorianCalendar().getTime(),
                fit.getFlight().getLandingDate().toGregorianCalendar().getTime(),
                fit.getFlight().getCarrierName());
        
    }
    
}
