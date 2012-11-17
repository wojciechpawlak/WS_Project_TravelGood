/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mkucharek
 */
public class InvalidStatusException extends WebApplicationException {

    public InvalidStatusException(String message) {
         super(Response.status(Response.Status.CONFLICT)
             .entity(message).type(MediaType.TEXT_PLAIN).build());
     }

}
