package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.Itinerary;

@XmlRootElement()
public class ItineraryStatus extends AbstractStatus {

    private Itinerary itinerary;

    public ItineraryStatus() {
    }

    public ItineraryStatus(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

}
