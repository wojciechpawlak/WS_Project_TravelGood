/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.niceview;

import dk.dtu.imm.fastmoney.types.CreditCardFaultType;
import javax.jws.WebService;
import ws.niceview.types.AddressType;
import ws.niceview.types.BookHotelFaultType;
import ws.niceview.types.CancelHotelFaultType;
import ws.niceview.types.HotelListType;
import ws.niceview.types.HotelType;

/**
 *
 * @author mkucharek
 */
@WebService(serviceName = "NiceViewWSDLService", portName = "NiceViewWSDLPort", endpointInterface = "ws.niceview.NiceViewWSDLPortType", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceViewWebServiceFromWSDL/NiceViewWSDL.wsdl")
public class NiceViewWebServiceFromWSDL {

    public ws.niceview.types.HotelListType getHotels(java.lang.String city, java.util.Calendar departureDate, java.util.Calendar arrivalDate) {
        HotelListType hotelList = new HotelListType();

        HotelType hotel = new HotelType();

        hotel.setBookingNumber("12345");
        hotel.setHotelName("Paradise");
        hotel.setHotelStayPrice(150.0);
        hotel.setIfCreditCardRequired(true);

        AddressType hotelAddress = new AddressType();

        hotelAddress.setStreet("testStreet");

        hotel.setHotelAddress(hotelAddress);

        hotelList.getHotel().add(hotel);

        return hotelList;

    }

    public boolean bookHotel(java.lang.String bookingNumber, dk.dtu.imm.fastmoney.types.CreditCardInfoType creditCardInfo) throws BookHotelFault, BookHotelCreditCardFault {
        if ("".equals(bookingNumber))
            throw new BookHotelFault("Booking number cannot be empty", new BookHotelFaultType());

        if ("".equals(creditCardInfo.getNumber()))
            throw new BookHotelCreditCardFault("Number cannot be empty", new CreditCardFaultType());
        return true;
    }

    public boolean cancelHotel(java.lang.String bookingNumber) throws CancelHotelFault {
        if ("".equals(bookingNumber))
            throw new CancelHotelFault("Booking number cannot be empty", new CancelHotelFaultType());
        return true;
    }

}
