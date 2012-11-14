/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.lameduck;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import org.junit.Test;
import org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightInformationType;

/**
 *
 * @author s120997
 */
public class LameDuckJUnit {


    @Test
    public void getFlightTest1() throws DatatypeConfigurationException {


        org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.LameDuckWSDLService service = new org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.LameDuckWSDLService();
        org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();
        // TODO initialize WS operation arguments here
        org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.RequestGetFlightType input = new org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.RequestGetFlightType();
        input.setFlightDestination("Berlin");
        input.setFlightStart("Moscow");

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar dateFlight = df.newXMLGregorianCalendar("2012-12-22");
        input.setFlightDate(dateFlight);

        // TODO process result here
        org.netbeans.j2ee.wsdl.lameduckservice.lameduckwsdl.FlightInformationListType result = port.getFlights(input);
        FlightInformationType myResultToPrint = result.getFlightInformation().get(0);
        
        System.out.println(myResultToPrint.getAirlineReservationServiceName() + "\n" +
                           myResultToPrint.getBookingNumber() + "\n" +
                           Double.toString(myResultToPrint.getPrice()) + "\n" +
                           myResultToPrint.getFlight().getCarrierName() + "\n" +
                           myResultToPrint.getFlight().getDestinationAirport() + "\n" +
                           myResultToPrint.getFlight().getStartAirport());

    }

}