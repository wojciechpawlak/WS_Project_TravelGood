package ws.niceview;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import dk.dtu.imm.fastmoney.types.CreditCardFaultType;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;
import ws.niceview.types.AddressType;
import ws.niceview.types.BookHotelFaultType;
import ws.niceview.types.CancelHotelFaultType;
import ws.niceview.types.HotelListType;
import ws.niceview.types.HotelType;

/**
 *
 * @author mkucharek
 */
@WebService(serviceName = "NiceViewWSDLService", portName = "NiceViewWSDLPort", endpointInterface = "ws.niceview.NiceViewWSDLPortType", targetNamespace = "http://niceview.ws", wsdlLocation = "WEB-INF/wsdl/NiceViewWebServiceFromWSDL/NiceViewWSDL.wsdl")
public class NiceViewWebServiceFromWSDL {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    public static int GROUP_NUMBER = 1; // 02267 Course Group nr. 1 - for BankService logging purposes
    public static AccountType account = new AccountType();
    private static int DAY_LIMIT = 14;
    private HotelType[] hotelArray = new HotelType[]{
        createHotel("Superb Hotel", "TestStreet", "12345", "Barcelona", "1", 50000.0, true),
        createHotel("Nice Hotel", "TestStreet", "12345", "Vienna", "2", 2000.0, true),
        createHotel("Passable Hotel", "TestStreet", "12345", "Vienna", "3", 500.0, true),
        createHotel("Shitty Hotel", "TestStreet", "12345", "Zgierz", "4", 10.0, false)
    };
    List<String> bookedHotels = new ArrayList<String>();

    public ws.niceview.types.HotelListType getHotels(java.lang.String city, java.util.Calendar departureDate, java.util.Calendar arrivalDate) {

        HotelListType hotelList = new HotelListType();
        boolean randomize = false;
        Random rand = null;

        if (arrivalDate.get(java.util.Calendar.DAY_OF_YEAR) -
                departureDate.get(java.util.Calendar.DAY_OF_YEAR) > DAY_LIMIT) {
            randomize = true;
            rand = new Random(System.currentTimeMillis());
        }

        for (HotelType hotel : hotelArray) {

            if (hotel.getHotelAddress().getCity().equalsIgnoreCase(city)) {
                if (randomize) {
                    if (rand.nextBoolean()) {
                        hotelList.getHotel().add(hotel);
                    }

                } else {
                    hotelList.getHotel().add(hotel);

                }
            }
        }

        return hotelList;

    }

    public boolean bookHotel(java.lang.String bookingNumber, CreditCardInfoType creditCardInfo) throws BookHotelFault, BookHotelCreditCardFault {
        if (bookingNumber == null || "".equals(bookingNumber)) {
            throw new BookHotelFault("Booking number was not provided", new BookHotelFaultType());
        }

        if (creditCardInfo == null) {
            throw new BookHotelCreditCardFault("Credit Card Info was not provided", new CreditCardFaultType());
        }

        if (creditCardInfo.getNumber() == null || "".equals(creditCardInfo.getNumber())) {
            throw new BookHotelCreditCardFault("Credit Card Number was not provided", new CreditCardFaultType());
        } else if (creditCardInfo.getName() == null || "".equals(creditCardInfo.getName())) {
            throw new BookHotelCreditCardFault("Credit Card Name is empty or was not provided", new CreditCardFaultType());
        } else if (creditCardInfo.getExpirationDate().getMonth() == 0 || creditCardInfo.getExpirationDate().getYear() == 0) {
            throw new BookHotelCreditCardFault("Credit Card Expiration Date was not provided", new CreditCardFaultType());
        } 

        HotelType currentHotelToBook = new HotelType();


        for (HotelType hotel : hotelArray) {
            if (bookingNumber.equals(hotel.getBookingNumber())) {
                currentHotelToBook = hotel;
                break;
            }
        }

        if (!bookingNumber.equals(currentHotelToBook.getBookingNumber())) {
            throw new BookHotelFault("Hotel for booking number " + bookingNumber + " does not exist", new BookHotelFaultType());
        }

        // Card Validation

        dk.dtu.imm.fastmoney.BankPortType port = service.getBankPort();

        int amount = (int) currentHotelToBook.getHotelStayPrice();

        try {
            boolean validationResult = port.validateCreditCard(GROUP_NUMBER, creditCardInfo, amount);
        } catch (CreditCardFaultMessage ex) {
            CreditCardFaultType fault = new CreditCardFaultType();
            fault.setMessage(ex.getMessage());
            throw new BookHotelCreditCardFault("Invalid credit card info provided or amount of money to be charged is not guaranteed", fault);
        }

        // Card Charging

        account.setName("NiceView");
        account.setNumber("50308815");

        try {
            boolean chargingResult = port.chargeCreditCard(GROUP_NUMBER, creditCardInfo, amount, account);
        } catch (CreditCardFaultMessage ex) {
            CreditCardFaultType fault = new CreditCardFaultType();
            fault.setMessage(ex.getMessage());
            throw new BookHotelCreditCardFault("Credit card cannot be charged", fault);
        }

        bookedHotels.add(currentHotelToBook.getBookingNumber());

        return true;

    }

    public boolean cancelHotel(java.lang.String bookingNumber) throws CancelHotelFault {
        if (bookingNumber == null || "".equals(bookingNumber)) {
            throw new CancelHotelFault("Booking number was not provided", new CancelHotelFaultType());
        }

        if (!bookedHotels.remove(bookingNumber)) {
            throw new CancelHotelFault("No booked hotels for booking number " + bookingNumber, new CancelHotelFaultType());
        }

        return true;
    }

    private HotelType createHotel(String hotelName, String hotelStreet, String hotelPostcode, String hotelCity, String bookingNumber, double stayPrice, boolean ifCreditCardGuaranteeRequired) {

        HotelType hotel = new HotelType();

        hotel.setHotelName(hotelName);

        AddressType hotelAddress = new AddressType();
        hotelAddress.setStreet(hotelStreet);
        hotelAddress.setPostcode(hotelPostcode);
        hotelAddress.setCity(hotelCity);
        hotel.setHotelAddress(hotelAddress);

        hotel.setBookingNumber(bookingNumber);
        hotel.setHotelStayPrice(stayPrice);
        hotel.setIfCreditCardRequired(ifCreditCardGuaranteeRequired);

        return hotel;
    }
}
