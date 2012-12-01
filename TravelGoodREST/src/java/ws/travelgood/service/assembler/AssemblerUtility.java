/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.service.assembler;

import java.util.ArrayList;
import java.util.List;
import ws.travelgood.domain.AddressEntity;
import ws.travelgood.domain.FlightBookingEntity;
import ws.travelgood.domain.HotelBookingEntity;
import ws.travelgood.domain.ItineraryEntity;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.flight.FlightBooking;
import ws.travelgood.types.hotel.Address;
import ws.travelgood.types.hotel.HotelBooking;

/**
 *
 * @author mkucharek
 */
public class AssemblerUtility {

    public static Itinerary toItinerary(ItineraryEntity ite) {

        if (ite == null) {
            return null;
        }

        List<HotelBooking> hbList = new ArrayList<HotelBooking>();

        for (HotelBookingEntity hbe : ite.getHotelBookingList()) {
            hbList.add(toHotelBooking(hbe));
        }

        List<FlightBooking> fbList = new ArrayList<FlightBooking>();

        for (FlightBookingEntity fbe : ite.getFlightBookingList()) {
            fbList.add(toFlightBooking(fbe));
        }

        return new Itinerary(ite.getUserId(), ite.getCurrentStatus(), hbList, fbList);
    }

    public static HotelBooking toHotelBooking(HotelBookingEntity hbe) {

        if (hbe == null) {
            return null;
        }

        return new HotelBooking(hbe.getHotelName(),
                new Address(hbe.getHotelAddress().getStreet(), hbe.getHotelAddress().getPostcode(), hbe.getHotelAddress().getCity()),
                hbe.getBookingNumber(),
                hbe.getPrice(),
                hbe.isIfCreditCardRequired());
        
    }

    public static HotelBookingEntity toHotelBookingEntity(HotelBooking hb) {

        if (hb == null) {
            return null;
        }

        return new HotelBookingEntity(null,
                hb.getBookingNumber(),
                hb.getPrice(),
                hb.getHotelName(),
                new AddressEntity(hb.getHotelAddress().getStreet(),
                    hb.getHotelAddress().getPostcode(),
                    hb.getHotelAddress().getCity()),
                hb.isIfCreditCardRequired());

    }

    public static FlightBooking toFlightBooking(FlightBookingEntity fbe) {

        if (fbe == null) {
            return null;
        }

        return new FlightBooking(fbe.getBookingNumber(),
                fbe.getPrice(),
                fbe.getAirlineName(),
                fbe.getStartAirport(),
                fbe.getDestinationAirport(),
                fbe.getLiftOffDate(),
                fbe.getLandingDate(),
                fbe.getCarrierName());
    }

    public static FlightBookingEntity toFlightBookingEntity(FlightBooking fb) {

        if (fb == null) {
            return null;
        }

        return new FlightBookingEntity(null,
                fb.getBookingNumber(),
                fb.getPrice(),
                fb.getAirlineName(),
                fb.getStartAirport(),
                fb.getDestinationAirport(),
                fb.getLiftOffDate(),
                fb.getLandingDate(),
                fb.getCarrierName());
    }

}
