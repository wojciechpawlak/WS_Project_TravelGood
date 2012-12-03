/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain.banking;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mkucharek
 */
@XmlRootElement
public class CreditCardInfo {
    private ExpirationDate expirationDate;
    private String name;
    private String number;

    public CreditCardInfo() {
    }

    public CreditCardInfo(ExpirationDate expirationDate, String name,
            String number) {
        this.expirationDate = expirationDate;
        this.name = name;
        this.number = number;
    }

    /**
     * @return the expirationDate
     */
    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(ExpirationDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }
    
}
