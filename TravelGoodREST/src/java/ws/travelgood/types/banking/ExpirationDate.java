/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.types.banking;

/**
 *
 * @author mkucharek
 */
public class ExpirationDate {

    protected int month;
    protected int year;

    public ExpirationDate() {

    }

    public ExpirationDate(int month, int year) {
        this.month = month;
        this.year = year;
    }

    

    /**
     * Gets the value of the month property.
     *
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     *
     */
    public void setMonth(int value) {
        this.month = value;
    }

    /**
     * Gets the value of the year property.
     *
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     *
     */
    public void setYear(int value) {
        this.year = value;
    }

}