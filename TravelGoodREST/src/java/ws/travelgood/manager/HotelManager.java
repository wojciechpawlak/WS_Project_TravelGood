/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.manager;

import java.util.Date;
import ws.travelgood.types.hotel.HotelList;

/**
 *
 * @author mkucharek
 */
public interface HotelManager {

    public HotelList getHotels(Date dateFrom, Date dateTo, String cityName);

}
