/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.travelgood.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
@XmlEnum(String.class)
public enum ItineraryStatus {

    @XmlEnumValue("PLANNING")
    PLANNING,
    @XmlEnumValue("BOOKED")
    BOOKED,
    @XmlEnumValue("CANCEL_REQUESTED")
    CANCEL_REQUESTED;
    
}
