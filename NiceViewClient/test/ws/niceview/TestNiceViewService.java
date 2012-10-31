/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.niceview;

import java.util.Calendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kucharekm
 */
public class TestNiceViewService {

    public TestNiceViewService() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void hello() {


        try { // Call Web Service Operation
            ws.niceview.NiceViewWSDLService service = new ws.niceview.NiceViewWSDLService();
            ws.niceview.NiceViewWSDLPortType port = service.getNiceViewWSDLPort();
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
}
