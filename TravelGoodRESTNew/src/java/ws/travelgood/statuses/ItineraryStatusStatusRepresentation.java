package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.ItineraryStatus;

@XmlRootElement()
public class ItineraryStatusStatusRepresentation extends AbstractStatusRepresentation {

    private ItineraryStatus itineraryStatus;

    public ItineraryStatusStatusRepresentation() {
    }

    public ItineraryStatusStatusRepresentation(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    public ItineraryStatus getItineraryStatus() {
        return itineraryStatus;
    }

    public void setItineraryStatus(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    


}
