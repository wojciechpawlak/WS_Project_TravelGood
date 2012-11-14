/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.niceview;

import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
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


        try { // Call Web Service Operation

            // TODO initialize WS operation arguments here
            java.lang.String city = "";
            Calendar departureDate = Calendar.getInstance();
            Calendar arrivalDate = Calendar.getInstance();
            // TODO process result here
            ws.niceview.types.HotelListType result = port.getHotels(city, departureDate, arrivalDate);

            assertEquals(1, result.getHotel().size());
            System.out.println("Result = " + result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            fail();
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

    private void testCancelHotel(String bookingNumber,boolean expectedToPass) {

        try {

            boolean result = port.cancelHotel(bookingNumber);

            assertTrue(expectedToPass);

        } catch (CancelHotelFault ex) {
            assertFalse(expectedToPass);

        }

    }
   
}
