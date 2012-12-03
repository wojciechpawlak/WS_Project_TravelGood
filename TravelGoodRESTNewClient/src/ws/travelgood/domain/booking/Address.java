/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain.booking;

/**
 *
 * @author mkucharek
 */
public class Address {

    private String street;
    private String postcode;
    private String city;

    public Address() {

    }

    public Address(String street, String postcode, String city) {
        this.street = street;
        this.postcode = postcode;
        this.city = city;

    }

    public Address(Address a) {
        this(a.street, a.postcode, a.city);
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

}
