package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.Itinerary;

@XmlRootElement()
public class ItineraryStatusRepresentation extends AbstractStatusRepresentation {

    private Itinerary itinerary;

    public ItineraryStatusRepresentation() {
    }

    public ItineraryStatusRepresentation(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

}
