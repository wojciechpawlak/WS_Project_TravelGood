/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.lameduck.LameDuckWSDLPortType;
import ws.lameduck.LameDuckWSDLService;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.RequestGetFlightType;
import ws.travelgood.manager.BookingException;
import ws.travelgood.manager.BookingManager;
import ws.travelgood.manager.FlightManager;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.flight.FlightList;

/**
 *
 * @author mkucharek
 */
public class LameDuckManager implements FlightManager, BookingManager {

    private LameDuckWSDLPortType port;

    private LameDuckManager() {
        port = new LameDuckWSDLService().getLameDuckWSDLPortTypeBindingPort();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class LameDuckManagerHolder {

        public static final LameDuckManager INSTANCE = new LameDuckManager();
    }

    public static LameDuckManager getInstance() {
        return LameDuckManagerHolder.INSTANCE;
    }

    public FlightList getFlights(Date date, String cityFromName,
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

        return new FlightList(result.getFlightInformation());

    }

    public boolean book(String bookingNumber, CreditCardInfo ccInfo) throws BookingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean cancel(String bookingNumber) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
