/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.hotel;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.niceview.types.HotelType;

/**
 *
 * @author mkucharek
 */
@XmlRootElement(name="hotelList")
public class HotelList {

    @XmlElement(name="hotel")
    private List<HotelType> hotelList;
    
    private HotelList() {

    }

    public HotelList(List<HotelType> hotelList) {
        this.hotelList = hotelList;
    }

    /**
     * @return the hotelList
     */
    public List<HotelType> getHotelList() {
        return hotelList;
    }

}
