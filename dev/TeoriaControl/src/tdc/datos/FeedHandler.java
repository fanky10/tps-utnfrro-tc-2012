/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;

import java.util.EventListener;

/**
 *
 * @author fanky
 */
public interface FeedHandler extends EventListener{
    public void eventChanged(FeedEvent evt);
}
