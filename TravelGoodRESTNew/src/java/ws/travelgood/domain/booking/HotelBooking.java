/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain.booking;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class HotelBooking extends AbstractBooking {

    private String hotelName;
    private Address hotelAddress;
    private boolean ifCreditCardRequired;

    private Date dateFrom;
    private Date dateTo;
    private String city;

    public HotelBooking() {
        super(BookingStatus.UNCONFIRMED, "", 0.0);
    }

    public HotelBooking(BookingStatus bookingStatus, String bookingNumber, double price) {
        super(bookingStatus, bookingNumber, price);
    }

    public HotelBooking(BookingStatus bookingStatus, String bookingNumber, double price,
            String hotelName, Address hotelAddress,
            boolean ifCreditCardRequired, Date dateFrom, Date dateTo, String city) {
        super(bookingStatus, bookingNumber, price);
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.ifCreditCardRequired = ifCreditCardRequired;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.city = city;
    }

    public HotelBooking(HotelBooking hb) {
        this(hb.getBookingStatus(), 
                hb.getBookingNumber(),
                hb.getPrice(),
                hb.hotelName,
                new Address(hb.getHotelAddress()),
                hb.ifCreditCardRequired,
                hb.dateFrom,
                hb.dateTo,
                hb.city);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    

}
