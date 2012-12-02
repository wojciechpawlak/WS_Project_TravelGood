/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.hotel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.states.BookingStatus;
import ws.travelgood.types.Booking;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelBooking extends Booking {

    private String hotelName;
    private Address hotelAddress;
    private boolean ifCreditCardRequired;

    private HotelBooking() {
        super(null, "", 0.0);
        
    }

    public HotelBooking(BookingStatus bookingStatus, String hotelName, Address hotelAddress, String bookingNumber, double price, boolean ifCreditCardRequired) {
        super(bookingStatus, bookingNumber, price);

        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.ifCreditCardRequired = ifCreditCardRequired;

    }

    /**
     * @return the hotelName
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * @param hotelName the hotelName to set
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * @return the hotelAddress
     */
    @XmlElement()
    public Address getHotelAddress() {
        return hotelAddress;
    }

    /**
     * @param hotelAddress the hotelAddress to set
     */
    public void setHotelAddress(Address hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    /**
     * @return the ifCreditCardRequired
     */
    public boolean isIfCreditCardRequired() {
        return ifCreditCardRequired;
    }

    /**
     * @param ifCreditCardRequired the ifCreditCardRequired to set
     */
    public void setIfCreditCardRequired(boolean ifCreditCardRequired) {
        this.ifCreditCardRequired = ifCreditCardRequired;
    }

}
