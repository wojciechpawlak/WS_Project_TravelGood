/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types;

/**
 *
 * @author mkucharek
 */
public abstract class Booking {

    private String bookingType;

    private String bookingNumber;

    private Booking() {

    }
    
    protected Booking(String bookingType, String bookingNumber) {
        this.bookingNumber = bookingNumber;
        this.bookingType = bookingType;
        
    }

    /**
     * @return the bookingType
     */
    public String getBookingType() {
        return bookingType;
    }

    /**
     * @return the bookingNumber
     */
    public String getBookingNumber() {
        return bookingNumber;
    }

    /**
     * @param bookingType the bookingType to set
     */
    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    /**
     * @param bookingNumber the bookingNumber to set
     */
    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

}
