/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws.travelgood.domain;

/**
 *
 * @author mkucharek
 */
public abstract class AbstractEntity implements Comparable<AbstractEntity> {

    private Integer id;

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int compareTo(AbstractEntity o) {
        return this.getId().compareTo(o.getId());
    }

}
