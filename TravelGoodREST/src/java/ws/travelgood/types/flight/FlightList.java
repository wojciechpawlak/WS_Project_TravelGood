/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.flight;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.lameduck.types.FlightInformationType;

/**
 *
 * @author mkucharek
 */
@XmlRootElement(name="flightList")
public class FlightList {

    @XmlElement(name="flight")
    private List<FlightInformationType> flightList;
    
    private FlightList() {

    }

    public FlightList(List<FlightInformationType> flightList) {
        this.flightList = flightList;
    }

    /**
     * @return the hotelList
     */
    public List<FlightInformationType> getHotelList() {
        return flightList;
    }

}
