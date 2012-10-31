/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.lameduck;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightInformationListType;
import org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightInformationType;
import org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightType;

/**
 *
 * @author s120997
 */
@WebService(serviceName = "LameDuckWSDLService", portName = "LameDuckWSDLPortTypeBindingPort", endpointInterface = "org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.LameDuckWSDLPortType", targetNamespace = "http://j2ee.netbeans.org/wsdl/LameDuckService/LameDuckWSDL", wsdlLocation = "WEB-INF/wsdl/LameDuckService/LameDuckWSDL.wsdl")
public class LameDuckService {

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


        myListOfFlights.getFlightInformation().add(myFlightInformation);

        return myListOfFlights;
    }

}
