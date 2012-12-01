/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

/**
 *
 * @author mkucharek
 */
public abstract class BookingEntity extends AbstractEntity {

    private String bookingNumber;

    private double price;

    public BookingEntity(Integer id, String bookingNumber, double price) {
        super(id);
        this.bookingNumber = bookingNumber;
        this.price = price;
    }

    /**
     * @return the bookingNumber
     */
    public String getBookingNumber() {
        return bookingNumber;
    }

    /**
     * @param bookingNumber the bookingNumber to set
     */
    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
}
