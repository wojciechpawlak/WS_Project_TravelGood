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
public class FlightBooking extends AbstractBooking {

    private String airlineName;
    private String startAirport;
    private String destinationAirport;
    private Date liftOffDate;
    private Date landingDate;
    private String carrierName;

    public FlightBooking() {
        super(BookingStatus.UNCONFIRMED, "", 0.0);
    }

    public FlightBooking(BookingStatus bookingStatus, String bookingNumber, double price) {
        super(bookingStatus, bookingNumber, price);
    }

    public FlightBooking(BookingStatus bookingStatus, String bookingNumber, double price,
            String airlineName, String startAirport, String destinationAirport,
            Date liftOffDate, Date landingDate, String carrierName) {
        super(bookingStatus, bookingNumber, price);
        this.airlineName = airlineName;
        this.startAirport = startAirport;
        this.destinationAirport = destinationAirport;
        this.liftOffDate = liftOffDate;
        this.landingDate = landingDate;
        this.carrierName = carrierName;
    }

    public FlightBooking(FlightBooking fb) {
        this(fb.getBookingStatus(),
                fb.getBookingNumber(),
                fb.getPrice(),
                fb.airlineName,
                fb.startAirport,
                fb.destinationAirport,
                fb.liftOffDate,
                fb.landingDate,
                fb.carrierName);
    }

    /**
     * @return the airlineName
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * @param airlineName the airlineName to set
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * @return the startAirport
     */
    public String getStartAirport() {
        return startAirport;
    }

    /**
     * @param startAirport the startAirport to set
     */
    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }

    /**
     * @return the destinationAirport
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * @param destinationAirport the destinationAirport to set
     */
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    /**
     * @return the liftOffDate
     */
    public Date getLiftOffDate() {
        return liftOffDate;
    }

    /**
     * @param liftOffDate the liftOffDate to set
     */
    public void setLiftOffDate(Date liftOffDate) {
        this.liftOffDate = liftOffDate;
    }

    /**
     * @return the landingDate
     */
    public Date getLandingDate() {
        return landingDate;
    }

    /**
     * @param landingDate the landingDate to set
     */
    public void setLandingDate(Date landingDate) {
        this.landingDate = landingDate;
    }

    /**
     * @return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * @param carrierName the carrierName to set
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    
}
