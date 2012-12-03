package ws.travelgood.statuses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractStatusRepresentation {

    @XmlElement(name = "link")
    private List<Link> links = new ArrayList<Link>();

    public List<Link> getLinks() {
        return links;
    }

    public Link getLinkByName(String uriName) {
        if (links == null) {
            return null;
        }
        for (Link l : links) {
            if (l.getRel().toLowerCase().equals(uriName.toLowerCase())) {
                return l;
            }
        }
        return null;
    }
}
