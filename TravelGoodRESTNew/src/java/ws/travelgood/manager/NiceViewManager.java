/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.niceview.BookHotelCreditCardFault;
import ws.niceview.BookHotelFault;
import ws.niceview.CancelHotelFault;
import ws.niceview.NiceViewWSDLPortType;
import ws.niceview.NiceViewWSDLService;
import ws.niceview.types.HotelListType;
import ws.niceview.types.HotelType;
import ws.travelgood.domain.banking.CreditCardInfo;
import ws.travelgood.domain.booking.Address;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 * @author wpawlak
 */
public class NiceViewManager implements HotelManager {

    private NiceViewWSDLPortType port;

    public NiceViewManager() {
        port = new NiceViewWSDLService().getNiceViewWSDLPort();
    }

    public List<HotelBooking> getHotels(Date dateFrom, Date dateTo, String cityName) {

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

        List<HotelBooking> hbList = new ArrayList<HotelBooking>();

        for (HotelType ht : result.getHotel()) {
            hbList.add(toHotelBooking(ht, dateFrom, dateTo, cityName));

        }

        return hbList;

    }

    public boolean book(HotelBooking booking, CreditCardInfo ccInfo) throws
            BookingException {

        try {

            CreditCardInfoType ccit = new CreditCardInfoType();
            ccit.setName(ccInfo.getName());
            ccit.setNumber(ccInfo.getNumber());

            ExpirationDateType edt = new ExpirationDateType();
            edt.setMonth(ccInfo.getExpirationDate().getMonth());
            edt.setYear(ccInfo.getExpirationDate().getYear());
            ccit.setExpirationDate(edt);

            return port.bookHotel(booking.getBookingNumber(), ccit);

        } catch (BookHotelCreditCardFault e) {
            // booking failed due to wrong credit card info
            throw new BookingException("Hotel Booking [bookingNumber=" +
                    booking.getBookingNumber() + "] failed - credit card info rejected", e);

        } catch (BookHotelFault e) {
            throw new BookingException("Hotel Booking [bookingNumber=" +
                    booking.getBookingNumber() + "] failed - internal error", e);
        }

    }

    public boolean cancel(HotelBooking booking, CreditCardInfo ccInfo) throws BookingException {
        try {
            port.cancelHotel(booking.getBookingNumber());
            return true;

        } catch (CancelHotelFault e) {
            throw new BookingException("Cancel failed for HotelBooking[bookingNumber=" +
                    booking.getBookingNumber() + "]", e);

        }
    }

    private HotelBooking toHotelBooking(HotelType ht, Date dateFrom, Date dateTo, String city) {
        return new HotelBooking(
                null,
                ht.getBookingNumber(),
                ht.getHotelStayPrice(),
                ht.getHotelName(),
                new Address(ht.getHotelAddress().getStreet(),
                    ht.getHotelAddress().getPostcode(),
                    ht.getHotelAddress().getCity())
                , ht.isIfCreditCardRequired(),
                dateFrom,
                dateTo,
                city);
    }
}
