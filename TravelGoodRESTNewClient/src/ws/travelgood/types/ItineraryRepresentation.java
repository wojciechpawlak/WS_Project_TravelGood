package ws.travelgood.types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.Itinerary;

@XmlRootElement()
public class ItineraryRepresentation extends Representation {

    private Itinerary itinerary;

    public ItineraryRepresentation() {
    }

    public ItineraryRepresentation(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    @XmlElement
    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

}
