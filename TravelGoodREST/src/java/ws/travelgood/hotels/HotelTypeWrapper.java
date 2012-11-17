/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.hotels;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelTypeWrapper extends ws.niceview.types.HotelType {

    public HotelTypeWrapper(ws.niceview.types.HotelType hotel) {
        this.bookingNumber = hotel.getBookingNumber();
        this.hotelAddress = hotel.getHotelAddress();
        this.hotelName = hotel.getHotelName();
        this.hotelStayPrice = hotel.getHotelStayPrice();
        this.ifCreditCardRequired = hotel.isIfCreditCardRequired();
        
    }

}
