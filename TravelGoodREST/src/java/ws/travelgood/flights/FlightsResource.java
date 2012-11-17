/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.flights;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author mkucharek
 */
public class FlightsResource {

    @Context
    private UriInfo context;

    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
}
