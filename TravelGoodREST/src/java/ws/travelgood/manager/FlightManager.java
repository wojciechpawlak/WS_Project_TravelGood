/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.manager;

import java.util.Date;
import ws.travelgood.types.flight.FlightList;

/**
 *
 * @author mkucharek
 */
public interface FlightManager {

    public FlightList getFlights(Date date, String cityFromName,
            String cityToName);
}
