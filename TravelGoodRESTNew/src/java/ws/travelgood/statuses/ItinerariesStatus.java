/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.statuses;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.Itinerary;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class ItinerariesStatus extends AbstractStatus {

    private List<Itinerary> itineraryList;

    public ItinerariesStatus() {
    }

    public ItinerariesStatus(List<Itinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }

    @XmlElementWrapper(name="itineraries")
    @XmlElement(name="itinerary")
    public List<Itinerary> getItineraryList() {
        return itineraryList;
    }

    public void setItineraryList(List<Itinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }



}
