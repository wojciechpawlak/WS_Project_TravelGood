/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.lameduck;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.*;

/**
 *
 * @author s120997
 */
@WebService(serviceName = "LameDuckWSDLService", portName = "LameDuckWSDLPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.LameDuckWSDLPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/LameDuckService/LameDuckWSDL", wsdlLocation = "WEB-INF/wsdl/LameDuckWSDLService/LameDuckWSDL.wsdl")
public class LameDuckWSDLService {

    public org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightInformationListType getFlights(org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.RequestGetFlightType input) {
        String start = input.getFlightStart();
        String destination = input.getFlightDestination();
        XMLGregorianCalendar dateFlight = input.getFlightDate();

        //System.out.println("Start = " + start + "\nDestination = " + destination + "\nDate = " + dateFlight.toString());

        FlightInformationListType myListOfFlights = new FlightInformationListType();

        FlightInformationType myFlightInformation = new FlightInformationType();
        myFlightInformation.setBookingNumber("12345A");
        myFlightInformation.setAirlineReservationServiceName("SAS");
        myFlightInformation.setPrice(12.34);
        FlightType myFlight = new FlightType();
        myFlight.setCarrierName("Scandinavians Airlines");
        myFlight.setDestinationAirport("Berlin");
        myFlight.setStartAirport("Moscow");
        myFlight.setLiftOffDate(dateFlight);
        myFlight.setLandingDate(dateFlight);
        myFlightInformation.setFlight(myFlight);

        FlightInformationType myFlightInformation2 = new FlightInformationType();
        myFlightInformation2.setBookingNumber("54321B");
        myFlightInformation2.setAirlineReservationServiceName("AirAustria");
        myFlightInformation2.setPrice(21.43);
        FlightType myFlight2 = new FlightType();
        myFlight2.setCarrierName("Nikki");
        myFlight2.setStartAirport("Copenhagen");
        myFlight2.setDestinationAirport("Bucharest");
        myFlight2.setLiftOffDate(dateFlight);
        myFlight2.setLandingDate(dateFlight);
        myFlightInformation2.setFlight(myFlight2);


        myListOfFlights.getFlightInformation().add(myFlightInformation);
        myListOfFlights.getFlightInformation().add(myFlightInformation2);

        return myListOfFlights;
    }

    public boolean bookFlight(java.lang.String part1) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void cancelFlight(org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.RequestCancelFlightType part1) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }


}
