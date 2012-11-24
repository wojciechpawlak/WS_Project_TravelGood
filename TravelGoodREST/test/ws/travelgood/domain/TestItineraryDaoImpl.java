/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ws.travelgood.manager.impl.TravelGoodManager;
import ws.travelgood.types.Itinerary;
import ws.travelgood.types.ItineraryStatus;


/**
 *
 * @author mkucharek
 */
public class TestItineraryDaoImpl {
    
    private int itineraryCount;

    @Before
    public void setUp() {

        this.itineraryCount = TravelGoodManager.getInstance().getAllItineraries().size();
    }

    @Test
    public void TestGetAllItineraries() {

        Assert.assertEquals(3, TravelGoodManager.getInstance().getAllItineraries().size());

        Assert.assertEquals("u1", TravelGoodManager.getInstance().getAllItineraries().get(0).getUserId());
        Assert.assertEquals(1, TravelGoodManager.getInstance().getAllItineraries().get(0).getId().intValue());
        Assert.assertEquals(ItineraryStatus.PLANNING, TravelGoodManager.getInstance().getAllItineraries().get(0).getCurrentStatus());

        Assert.assertEquals("u1", TravelGoodManager.getInstance().getAllItineraries().get(1).getUserId());
        Assert.assertEquals(2, TravelGoodManager.getInstance().getAllItineraries().get(1).getId().intValue());
        Assert.assertEquals(ItineraryStatus.PLANNING, TravelGoodManager.getInstance().getAllItineraries().get(1).getCurrentStatus());

        Assert.assertEquals("u2", TravelGoodManager.getInstance().getAllItineraries().get(2).getUserId());
        Assert.assertEquals(3, TravelGoodManager.getInstance().getAllItineraries().get(2).getId().intValue());
        Assert.assertEquals(ItineraryStatus.BOOKED, TravelGoodManager.getInstance().getAllItineraries().get(2).getCurrentStatus());

    }

    @Test
    public void testGetUserItineraries() {

        Assert.assertEquals(2,TravelGoodManager.getInstance().getUserItineraries("u1").size());
        Assert.assertEquals(1,TravelGoodManager.getInstance().getUserItineraries("u2").size());
        Assert.assertEquals(0,TravelGoodManager.getInstance().getUserItineraries("unknown_user").size());

    }

    @Test
    public void testGetItinerary() {
        Itinerary retIt;
        Integer id;

        id = 1;
        retIt = TravelGoodManager.getInstance().getItinerary(id);
        testItinerary(retIt, id, "u1", ItineraryStatus.PLANNING);

        id = 2;
        retIt = TravelGoodManager.getInstance().getItinerary(id);
        testItinerary(retIt, id, "u1", ItineraryStatus.PLANNING);

        id = 3;
        retIt = TravelGoodManager.getInstance().getItinerary(id);
        testItinerary(retIt, id, "u2", ItineraryStatus.BOOKED);

        id = 700;
        retIt = TravelGoodManager.getInstance().getItinerary(id);
        Assert.assertNull(retIt);

    }

    @Test
    public void testCreateItinerary() {

        String userId;
        Itinerary retIt;

        userId = "u2";
        retIt = TravelGoodManager.getInstance().createItinerary(new Itinerary(userId));

        this.testItinerary(retIt, ++itineraryCount, userId,
                ItineraryStatus.PLANNING);

        userId = "u3";
        retIt = TravelGoodManager.getInstance().createItinerary(new Itinerary(userId));

        this.testItinerary(retIt, ++itineraryCount, userId,
                ItineraryStatus.PLANNING);


        retIt = TravelGoodManager.getInstance().createItinerary(new Itinerary(userId, 9000, ItineraryStatus.BOOKED));

        this.testItinerary(retIt, ++itineraryCount, userId,
                ItineraryStatus.BOOKED);


    }

    

    @Test
    public void testAddRemoveHotel() {
        
        Integer id = 1;

        String bookingNumber = "123";

        testAddHotel(id, bookingNumber, true);

        // add it one more time - should fail
        testAddHotel(id, bookingNumber, false);

        // add hotel to inexisting itineraryId - should return null
        Assert.assertFalse(TravelGoodManager.getInstance().addHotel(102912, "whateva"));

        // add hotel to already booked itinerary
        Assert.assertFalse(TravelGoodManager.getInstance().addHotel(3, "whateva"));
        

    }

    private void testItinerary(Itinerary it, Integer expectedId, String expectedUserId, ItineraryStatus expectedStatus) {
        Assert.assertNotNull(it);

        Assert.assertEquals(expectedId, it.getId());
        Assert.assertEquals(expectedUserId, it.getUserId());
        Assert.assertEquals(expectedStatus, it.getCurrentStatus());

    }

    private void testAddHotel(Integer itineraryId, String bookingNumber, boolean expectedResult) {

        int previousHotelCount = TravelGoodManager.getInstance().getItinerary(itineraryId).getHotelBookingList().size();

        boolean added = TravelGoodManager.getInstance().addHotel(itineraryId, bookingNumber);

        Assert.assertEquals(expectedResult, added);
        if (expectedResult) {
            Assert.assertEquals(previousHotelCount + 1, TravelGoodManager.getInstance().getItinerary(itineraryId).getHotelBookingList().size());
            Assert.assertNotNull(TravelGoodManager.getInstance().getItinerary(itineraryId).getHotelBooking(bookingNumber));

        } else {
            Assert.assertEquals(previousHotelCount, TravelGoodManager.getInstance().getItinerary(itineraryId).getHotelBookingList().size());
            Assert.assertNotNull(TravelGoodManager.getInstance().getItinerary(itineraryId).getHotelBooking(bookingNumber));

        }

    }

}