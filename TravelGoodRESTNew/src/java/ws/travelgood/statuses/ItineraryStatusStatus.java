package ws.travelgood.statuses;

import javax.xml.bind.annotation.XmlRootElement;
import ws.travelgood.domain.ItineraryStatus;

@XmlRootElement()
public class ItineraryStatusStatus extends AbstractStatus {

    private ItineraryStatus itineraryStatus;

    public ItineraryStatusStatus() {
    }

    public ItineraryStatusStatus(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    public ItineraryStatus getItineraryStatus() {
        return itineraryStatus;
    }

    public void setItineraryStatus(ItineraryStatus itineraryStatus) {
        this.itineraryStatus = itineraryStatus;
    }

    


}
