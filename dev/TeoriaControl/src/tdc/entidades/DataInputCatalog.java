/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author fanky
 */
public class DataInputCatalog extends ArrayList<DataInput> implements Comparator<DataInput>{
    
    public int compare(DataInput o1, DataInput o2) {
        if(o1.getTau()==o2.getTau()){
            return 0;
        }
        return (o1.getTau()>o2.getTau())?1:-1;
    }
    
    public void autoSort(){
        Collections.sort(this, this);
    }

}
