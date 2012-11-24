/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.flights;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.lameduck.LameDuckWSDLPortType;
import ws.lameduck.LameDuckWSDLService;
import ws.lameduck.types.FlightInformationListType;
import ws.lameduck.types.RequestGetFlightType;
import ws.travelgood.types.flight.FlightList;

/**
 *
 * @author mkucharek
 */
public class FlightsResource {

    @Context
    private UriInfo context;

    public FlightsResource() {
        
    }

    @GET
    @Produces("application/xml")
    public Response getFlights(@QueryParam("cityFrom") String cityFrom,
            @QueryParam("cityTo") String cityTo,
            @QueryParam("date") String dateStr) {

        LameDuckWSDLService service =
                new LameDuckWSDLService();
        LameDuckWSDLPortType port = service.getLameDuckWSDLPortTypeBindingPort();

        XMLGregorianCalendar departureDate;

        try {
            departureDate =
                    DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(dateStr);

        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException(ex);
        }

        RequestGetFlightType input = new RequestGetFlightType();

        input.setFlightDate(departureDate);
        input.setFlightStart(cityFrom);
        input.setFlightDestination(cityTo);

        FlightInformationListType result = port.getFlights(input);

        return Response.ok(new FlightList(result.getFlightInformation())).build();

    }

    @Path("{bookingNumber}")
    public FlightResource getFlightResource() {
        return new FlightResource();
    }
    
}
