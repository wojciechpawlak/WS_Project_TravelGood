/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service.impl;

import java.util.Calendar;
import ws.travelgood.domain.Itinerary;
import ws.travelgood.domain.ItineraryStatus;
import ws.travelgood.domain.booking.FlightBooking;
import ws.travelgood.domain.booking.HotelBooking;

/**
 *
 * @author mkucharek
 */
class ExpirationUtils {

    static final boolean ENABLED = false;

    private static final int DAYS_LEFT_TO_EXPIRE = 1;

    public static boolean canBeCancelled(Itinerary it) {
        return !isExpired(it);
    }

    public static boolean isExpired(Itinerary it) {

        if (!ENABLED) {
            return false;
        }

        if (it == null) {
            return true;

        }

        if (!it.getCurrentStatus().equals(ItineraryStatus.BOOKED)) {
            // we should only checked the booked itineraries
            return false;
        }

        // set up the date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -DAYS_LEFT_TO_EXPIRE);

        for (HotelBooking hb : it.getHotelBookingList()) {
            try {
                if (cal.getTime().before(hb.getDateFrom())) {
                    return true;
                }
            } catch (NullPointerException e) {
                //continue
            }

        }

        for (FlightBooking fb : it.getFlightBookingList()) {
            try {
                if (cal.getTime().before(fb.getLiftOffDate())) {
                    return true;
                }
            } catch (NullPointerException e) {
                //continue
            }

        }

        return false;

    }

}
