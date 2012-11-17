/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.niceview;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
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
    CreditCardInfoType correctCreditCardInfo;
    CreditCardInfoType incorrectCreditCardInfo;

    @Before
    public void setUp() throws Exception {
        NiceViewWSDLService service = new ws.niceview.NiceViewWSDLService();
        this.port = service.getNiceViewWSDLPort();

        correctCreditCardInfo = new CreditCardInfoType();
        correctCreditCardInfo.setNumber("50408825");
        correctCreditCardInfo.setName("Thor-Jensen Claus");

        ExpirationDateType correctEDT = new ExpirationDateType();
        correctEDT.setMonth(5);
        correctEDT.setYear(9);
        correctCreditCardInfo.setExpirationDate(correctEDT);

        incorrectCreditCardInfo = new CreditCardInfoType();
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

        // bookingNumber not specified
        testBookHotel("", correctCreditCardInfo, false);

        // bookingNumber does not exist
        testBookHotel("test", correctCreditCardInfo, false);

        testBookHotel("2", correctCreditCardInfo, true);

        // not enough money on the valid account - hotel: 50000, money: 10000
        testBookHotel("1", correctCreditCardInfo, false);

        // creditCardInfo lacks number
        testBookHotel("4", incorrectCreditCardInfo, false);

        // creditCardInfto number specified is empty
        incorrectCreditCardInfo.setNumber("");
        testBookHotel("4", incorrectCreditCardInfo, false);

        incorrectCreditCardInfo.setNumber("50408822");

        // creditCardInfo lacks name
        testBookHotel("4", incorrectCreditCardInfo, false);

        incorrectCreditCardInfo.setName("Bech Camilla");

        // creditCardInfo lacks expiration date
        ExpirationDateType incorrectEDT = new ExpirationDateType();
        incorrectCreditCardInfo.setExpirationDate(incorrectEDT);
        testBookHotel("4", incorrectCreditCardInfo, false);
    }

    private void testBookHotel(String bookingNumber, CreditCardInfoType creditCardInfo, boolean expectedToPass) {

        try {

            boolean result = port.bookHotel(bookingNumber, creditCardInfo);

            assertTrue(expectedToPass);

        } catch (BookHotelCreditCardFault ex) {
            System.out.println(ex.getMessage());
            assertFalse(expectedToPass);

        } catch (BookHotelFault ex) {
            System.out.println(ex.getMessage());
            assertFalse(expectedToPass);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            assert(expectedToPass);
        }

    }

    @Test
    public void testCancelHotel() {

        testBookHotel("2", correctCreditCardInfo, true);
        testBookHotel("3", correctCreditCardInfo, true);

        testCancelHotel("2", true);
        testCancelHotel("3", true);

        testCancelHotel("", false);

        testCancelHotel("4", false);

    }

    private void testCancelHotel(String bookingNumber, boolean expectedToPass) {

        try {

            boolean result = port.cancelHotel(bookingNumber);
            assertTrue(expectedToPass);

        } catch (CancelHotelFault ex) {
            System.out.println(ex.getMessage());
            assertFalse(expectedToPass);

        }

    }
}
