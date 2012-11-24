/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.niceview.BookHotelCreditCardFault;
import ws.niceview.BookHotelFault;
import ws.niceview.NiceViewWSDLPortType;
import ws.niceview.NiceViewWSDLService;
import ws.niceview.types.HotelListType;
import ws.travelgood.manager.BookingException;
import ws.travelgood.manager.BookingManager;
import ws.travelgood.manager.HotelManager;
import ws.travelgood.types.banking.CreditCardInfo;
import ws.travelgood.types.hotel.HotelList;

/**
 *
 * @author mkucharek
 */
public class NiceViewManager implements HotelManager, BookingManager {

    private NiceViewWSDLPortType port;

    private NiceViewManager() {
        port = new NiceViewWSDLService().getNiceViewWSDLPort();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class NiceViewManagerHolder {

        public static final NiceViewManager INSTANCE = new NiceViewManager();
    }

    public static NiceViewManager getInstance() {
        return NiceViewManagerHolder.INSTANCE;
    }

    public HotelList getHotels(Date dateFrom, Date dateTo, String cityName) {

        GregorianCalendar dateFromGCal = new GregorianCalendar();
        dateFromGCal.setTime(dateFrom);

        GregorianCalendar dateToGCal = new GregorianCalendar();
        dateToGCal.setTime(dateFrom);

        XMLGregorianCalendar departureDate;
        XMLGregorianCalendar arrivalDate;

        try {
            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(dateFromGCal);

            arrivalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    dateToGCal);

        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);

        }

        HotelListType result = port.getHotels(cityName, departureDate,
                arrivalDate);

        return new HotelList(result.getHotel());

    }

    public boolean book(String bookingNumber, CreditCardInfo ccInfo) throws
            BookingException {
        
        try {
            return port.bookHotel(bookingNumber, ccInfo);

        } catch (BookHotelCreditCardFault e) {
            // booking failed due to wrong credit card info
            throw new BookingException("Hotel Booking [bookingNumber=" + bookingNumber + "] failed - credit card info rejected", e);

        } catch (BookHotelFault e) {
            throw new BookingException("Hotel Booking [bookingNumber=" + bookingNumber + "] failed - internal error", e);
        }

    }

    public boolean cancel(String bookingNumber) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
