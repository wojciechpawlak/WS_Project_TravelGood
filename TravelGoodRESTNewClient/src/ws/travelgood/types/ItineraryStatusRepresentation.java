package ws.travelgood.types;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.ItineraryStatus;

@XmlRootElement()
public class ItineraryStatusRepresentation extends Representation {

    private ItineraryStatus itineraryStatus;

    public ItineraryStatusRepresentation() {
    }

    public ItineraryStatusRepresentation(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    public ItineraryStatus getItineraryStatus() {
        return itineraryStatus;
    }

    public void setItineraryStatus(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    


}
