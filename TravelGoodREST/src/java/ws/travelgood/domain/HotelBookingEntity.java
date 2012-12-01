/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

/**
 *
 * @author mkucharek
 */
public class HotelBookingEntity extends BookingEntity {

    private String hotelName;
    private AddressEntity hotelAddress;
    private boolean ifCreditCardRequired;

    public HotelBookingEntity(Integer id, String bookingNumber, double price) {
        super(id, bookingNumber, price);
    }

    public HotelBookingEntity(Integer id, String bookingNumber, double price,
            String hotelName, AddressEntity hotelAddress,
            boolean ifCreditCardRequired) {
        super(id, bookingNumber, price);
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
    public AddressEntity getHotelAddress() {
        return hotelAddress;
    }

    /**
     * @param hotelAddress the hotelAddress to set
     */
    public void setHotelAddress(AddressEntity hotelAddress) {
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
