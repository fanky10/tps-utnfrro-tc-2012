/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;

import java.util.EventObject;

/**
 *
 * @author fanky
 */
public class FeedEvent extends EventObject{
    private double data;
    public FeedEvent(Object source){
        super(source);
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
    
}
