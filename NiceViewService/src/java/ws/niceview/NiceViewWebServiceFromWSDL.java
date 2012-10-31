/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.niceview;

import java.util.Calendar;
import javax.jws.WebService;
import ws.niceview.types.AddressType;
import ws.niceview.types.HotelListType;
import ws.niceview.types.HotelType;

/**
 *
 * @author kucharekm
 */
@WebService(serviceName = "NiceViewWSDLService", portName = "NiceViewWSDLPort", endpointInterface = "ws.niceview.NiceViewWSDLPortType", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceViewWebServiceFromWSDL/NiceViewWSDL.wsdl")
public class NiceViewWebServiceFromWSDL {

    public HotelListType getHotels(String city, Calendar departureDate, Calendar arrivalDate) {
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

}
