/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.lameduck;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import org.junit.Test;
import ws.lameduck.types.FlightInformationType;

/**
 *
 * @author s120997
 */
public class LameDuckJUnit {

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testGetFlights() throws DatatypeConfigurationException {



             ws.lameduck.LameDuckWSDLService service = new ws.lameduck.LameDuckWSDLService();
             ws.lameduck.LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();
             // TODO initialize WS operation arguments here
             ws.lameduck.types.RequestGetFlightType input = new ws.lameduck.types.RequestGetFlightType();
        input.setFlightDestination("Berlin");
        input.setFlightStart("Moscow");

        DatatypeFactory df = DatatypeFactory.newInstance();
        XMLGregorianCalendar dateFlight = df.newXMLGregorianCalendar("2012-12-25");
        input.setFlightDate(dateFlight);
             // TODO process result here
             ws.lameduck.types.FlightInformationListType result = port.getFlights(input);
        FlightInformationType myResultToPrint = result.getFlightInformation().get(0);

        System.out.println(myResultToPrint.getAirlineReservationServiceName() + "\n" +
                           myResultToPrint.getBookingNumber() + "\n" +
                           Double.toString(myResultToPrint.getPrice()) + "\n" +
                           myResultToPrint.getFlight().getCarrierName() + "\n" +
                           myResultToPrint.getFlight().getDestinationAirport() + "\n" +
                           myResultToPrint.getFlight().getStartAirport());




     }

     @Test
     public void testBookFlights() throws DatatypeConfigurationException {




         try { // Call Web Service Operation
             ws.lameduck.LameDuckWSDLService service = new ws.lameduck.LameDuckWSDLService();
             ws.lameduck.LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();
             //initialize WS operation arguments here
             ws.lameduck.types.RequestBookFlightType input = new ws.lameduck.types.RequestBookFlightType();
             input.setBookingNumber("12345A");

             CreditCardInfoType ccit = new CreditCardInfoType();
             ccit.setName("Anne Strandberg");
             ccit.setNumber("50408816");
             ExpirationDateType edt = new ExpirationDateType();
             edt.setMonth(5);
             edt.setYear(9);
             ccit.setExpirationDate(edt);

             input.setCreditCardInformation(ccit);

             // TODO process result here
             boolean result = port.bookFlights(input);
             System.out.println("Result = "+result);
         } catch (Exception ex) {
             System.out.println(ex.getMessage());
         }



     }
     @Test
     public void testCancelFlight() throws DatatypeConfigurationException {



         try { // Call Web Service Operation
             ws.lameduck.LameDuckWSDLService service = new ws.lameduck.LameDuckWSDLService();
             ws.lameduck.LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();
             //initialize WS operation arguments here
             ws.lameduck.types.RequestCancelFlightType input = new ws.lameduck.types.RequestCancelFlightType();
             input.setBookingNumber("12345A");
             input.setPrice(12);

             CreditCardInfoType ccit = new CreditCardInfoType();
             ccit.setName("Anne Strandberg");
             ccit.setNumber("50408816");
             ExpirationDateType edt = new ExpirationDateType();
             edt.setMonth(5);
             edt.setYear(9);
             ccit.setExpirationDate(edt);

             input.setCreditCardInformation(ccit);

             // TODO process result here
             boolean result = port.cancelFlight(input);
             System.out.println("Result = "+result);
         } catch (Exception ex) {
             System.out.println(ex.getMessage());
         }
     }
}
