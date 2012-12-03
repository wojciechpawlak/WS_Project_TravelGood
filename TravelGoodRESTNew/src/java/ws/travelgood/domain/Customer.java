/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mkucharek
 */
public class Customer extends AbstractEntity {

    protected String id;

    Map<String, Itinerary> itineraryMap;

    public Customer(String id) {
        this(id, new HashMap<String, Itinerary>());

    }

    public Customer(String id, Map<String, Itinerary> itineraryMap) {
        this.id = id;
        this.itineraryMap = itineraryMap;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Itinerary> getItineraryMap() {
        return itineraryMap;
    }

    public void addUpdateItinerary(Itinerary ite) {
        itineraryMap.put(ite.getId(), ite);
    }

}
