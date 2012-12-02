/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import ws.travelgood.states.BookingStatus;

/**
 *
 * @author mkucharek
 */
public abstract class BookingEntity extends AbstractEntity {

    private BookingStatus bookingStatus;

    private String bookingNumber;

    private double price;

    public BookingEntity(Integer id, BookingStatus bookingStatus, String bookingNumber, double price) {
        super(id);
        this.bookingStatus = bookingStatus;
        this.bookingNumber = bookingNumber;
        this.price = price;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
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
