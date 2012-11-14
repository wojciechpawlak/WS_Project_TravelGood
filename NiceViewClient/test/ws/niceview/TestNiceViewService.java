/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.niceview;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import ws.niceview.types.HotelListType;
import ws.niceview.types.HotelType;
import static org.junit.Assert.*;

/**
 *
 * @author kucharekm
 */
public class TestNiceViewService {

    private NiceViewWSDLPortType port;

    @Before
    public void setUp() throws Exception {
        NiceViewWSDLService service = new ws.niceview.NiceViewWSDLService();
        this.port = service.getNiceViewWSDLPort();

    }

    @Test
    public void testGetHotels() {

        // the getHotels method returns the actual hotels only if the 
        // difference between departure and arrival date is smaller than
        // 14 days. If the difference is more, it randomizes the output.
        // For testing, we need to make sure this difference is smaller.
        Calendar departureDate = Calendar.getInstance();
        Calendar arrivalDate = Calendar.getInstance();

        testGetHotels("Barcelona", departureDate, arrivalDate, new String[]{"Superb Hotel"});
        testGetHotels("Vienna", departureDate, arrivalDate, new String[]{"Nice Hotel", "Passable Hotel"});
        testGetHotels("Zgierz", departureDate, arrivalDate, new String[]{"Shitty Hotel"});

    }

    private void testGetHotels(String city, Calendar departureDate, Calendar arrivalDate, String[] expectedHotelNames) {
        HotelListType result = port.getHotels(city, departureDate, arrivalDate);

        assertEquals(expectedHotelNames.length, result.getHotel().size());

        for (String expectedHotelName : expectedHotelNames) {
            int occurrences = 0;
            for (HotelType hotel : result.getHotel()) {

                if (expectedHotelName.equalsIgnoreCase(hotel.getHotelName())) {
                    ++occurrences;
                }

            }

            assertEquals("The expected hotel names does not match the webservice output", 1, occurrences);

        }
    }

    @Test
    public void testBookHotel() {

        CreditCardInfoType creditCardInfo = new CreditCardInfoType();
        creditCardInfo.setNumber("123");

        testBookHotel("test1", creditCardInfo, true);
        testBookHotel("test2", creditCardInfo, true);

        testBookHotel("", creditCardInfo, false);

        creditCardInfo.setNumber("");

        testBookHotel("test1", creditCardInfo, false);
        testBookHotel("test2", creditCardInfo, false);


    }

    private void testBookHotel(String bookingNumber, CreditCardInfoType creditCardInfo, boolean expectedToPass) {

        try {

            boolean result = port.bookHotel(bookingNumber, creditCardInfo);

            assertTrue(expectedToPass);

        } catch (BookHotelCreditCardFault ex) {
            assertFalse(expectedToPass);

        } catch (BookHotelFault ex) {
            assertFalse(expectedToPass);

        }

    }

    @Test
    public void testCancelHotel() {

        testCancelHotel("test1", true);
        testCancelHotel("test2", true);

        testCancelHotel("", false);

    }

    private void testCancelHotel(String bookingNumber, boolean expectedToPass) {

        try {

            boolean result = port.cancelHotel(bookingNumber);

            assertTrue(expectedToPass);

        } catch (CancelHotelFault ex) {
            assertFalse(expectedToPass);

        }

    }
}
