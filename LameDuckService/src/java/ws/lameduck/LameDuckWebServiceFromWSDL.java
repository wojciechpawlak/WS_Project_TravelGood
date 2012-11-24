/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.lameduck;

import dk.dtu.imm.fastmoney.BankService;
import java.util.ArrayList;
import java.util.Vector;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.FlightInformationType;
import ws.lameduck.types.FlightType;

/**
 *
 * @author s120997
 */
@WebService(serviceName = "LameDuckWSDLService", portName = "LameDuckWSDLPortTypeBindingPort", endpointInterface = "ws.lameduck.LameDuckWSDLPortType", targetNamespace = "http://lameduck.ws", wsdlLocation = "WEB-INF/wsdl/LameDuckWSDLService/LameDuckWSDL.wsdl")
public class LameDuckWebServiceFromWSDL {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;

    static private Vector<FlightInformationType> flights = new  Vector<FlightInformationType>();
    static private ArrayList<String> bookedFlights = new ArrayList<String>();
    static boolean onlyOnce = false;

    public ws.lameduck.types.FlightInformationListType getFlights(ws.lameduck.types.RequestGetFlightType input) {
 //

        populateFlights();

        String start = input.getFlightStart();
        String destination = input.getFlightDestination();
        XMLGregorianCalendar dateFlight = input.getFlightDate();

        FlightInformationListType returnListOfFlights = new FlightInformationListType();

        //search through the list of flights and build
        // the list of matching flights, and return it
        for(FlightInformationType fit:flights){

            if(start.equals(fit.getFlight().getStartAirport()) &&
               destination.equals(fit.getFlight().getDestinationAirport()) &&
               dateFlight.equals(fit.getFlight().getLiftOffDate())
                    ){
                returnListOfFlights.getFlightInformation().add(fit);
            }

        }

        return returnListOfFlights;
    }

    public boolean cancelFlight(ws.lameduck.types.RequestCancelFlightType input) throws CancelFlightFault {
        int priceOfFlight = 0;
        //get price of flight
        for(FlightInformationType fit : flights)
            if(fit.getBookingNumber().equals(input.getBookingNumber())){
                priceOfFlight = (int) fit.getPrice();
                break;
            }


        try { // Call Web Service Operation
            dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
            //initialize WS operation arguments here
            int group = 1;
            dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo = (dk.dtu.imm.fastmoney.types.CreditCardInfoType)input.getCreditCardInformation();
            int amount = priceOfFlight;
            dk.dtu.imm.fastmoney.types.AccountType account = new dk.dtu.imm.fastmoney.types.AccountType();
            account.setName("LameDuck");
            account.setNumber("50208812");

            boolean result = port.refundCreditCard(group, creditCardInfo, amount, account);

            if(result == false)
                throw new CancelFlightFault("Credit Card Refunding Failed", null);
        } catch (Exception ex) {
            throw new CancelFlightFault("Credit Card Refunding Failed", null);
        }


        return true;
    }

    public boolean bookFlights(ws.lameduck.types.RequestBookFlightType input) throws BookFlightFault {


        int priceOfFlight = 0;
        //get price of flight
        for(FlightInformationType fit : flights)
            if(fit.getBookingNumber().equals(input.getBookingNumber())){
                priceOfFlight = (int) fit.getPrice();
                break;
            }

        try { // Call Web Service Operation
            dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
            //initialize WS operation arguments here
            int group = 1;
            dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo = (dk.dtu.imm.fastmoney.types.CreditCardInfoType) input.getCreditCardInformation();
            int amount = priceOfFlight;

            boolean result = port.validateCreditCard(group, creditCardInfo, amount);

            if(result == false)
                throw new BookFlightFault("Credit Card Validation Failed", null);
        } catch (Exception ex) {
            throw new BookFlightFault("Credit Card Validation Failed", null);
        }


        try { // Call Web Service Operation
            dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();
            //initialize WS operation arguments here
            int group = 1;
            dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo = (dk.dtu.imm.fastmoney.types.CreditCardInfoType) input.getCreditCardInformation();
            int amount = priceOfFlight;
            dk.dtu.imm.fastmoney.types.AccountType account = new dk.dtu.imm.fastmoney.types.AccountType();
//            account.setName(input.getCreditCardInformation().getName());
//            account.setNumber(input.getCreditCardInformation().getNumber());

            account.setName("LameDuck");
            account.setNumber("50208812");


            boolean result = port.chargeCreditCard(group, creditCardInfo, amount, account);
             if(result == false)
                throw new BookFlightFault("Credit Card Charging Failed", null);

        } catch (Exception ex) {
            throw new BookFlightFault("Credit Card Charging Failed", null);
        }



        bookedFlights.add(input.getBookingNumber());

        return true;
    }

    private void populateFlights() {

        //called only once, at the first interogation of the flights

        if(onlyOnce == true)
            return;

        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2012-12-25");

        FlightInformationType myFlightInformation = new FlightInformationType();
        myFlightInformation.setBookingNumber("12345A");
        myFlightInformation.setAirlineReservationServiceName("SAS");
        myFlightInformation.setPrice(12);
        FlightType myFlight = new FlightType();
        myFlight.setCarrierName("Scandinavians Airlines");
        myFlight.setDestinationAirport("Berlin");
        myFlight.setStartAirport("Moscow");
        myFlight.setLiftOffDate(date);
        myFlight.setLandingDate(date);
        myFlightInformation.setFlight(myFlight);

        date = df.newXMLGregorianCalendar("2012-12-22");

        FlightInformationType myFlightInformation2 = new FlightInformationType();
        myFlightInformation2.setBookingNumber("54321B");
        myFlightInformation2.setAirlineReservationServiceName("AirAustria");
        myFlightInformation2.setPrice(22);
        FlightType myFlight2 = new FlightType();
        myFlight2.setCarrierName("Nikki");
        myFlight2.setStartAirport("Copenhagen");
        myFlight2.setDestinationAirport("Bucharest");
        myFlight2.setLiftOffDate(date);
        myFlight2.setLandingDate(date);
        myFlightInformation2.setFlight(myFlight2);

        date = df.newXMLGregorianCalendar("2012-12-29");

        FlightInformationType myFlightInformation3 = new FlightInformationType();
        myFlightInformation3.setBookingNumber("123321C");
        myFlightInformation3.setAirlineReservationServiceName("AirFrance");
        myFlightInformation3.setPrice(22);
        FlightType myFlight3 = new FlightType();
        myFlight3.setCarrierName("Blast");
        myFlight3.setStartAirport("Paris");
        myFlight3.setDestinationAirport("Tokyo");
        myFlight3.setLiftOffDate(date);
        myFlight3.setLandingDate(date);
        myFlightInformation3.setFlight(myFlight3);

        flights.add(myFlightInformation);
        flights.add(myFlightInformation2);
        flights.add(myFlightInformation3);

        onlyOnce = true;
    }
}
