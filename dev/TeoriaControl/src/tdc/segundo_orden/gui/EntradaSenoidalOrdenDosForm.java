/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.gui;

import tdc.entidades.DataInput;
import tdc.entidades.DataInputCatalog;

/**
 *
 * @author Fanky10 <fanky10@gmail.com>
 */
public class EntradaSenoidalOrdenDosForm extends DataInputCatalog {

    private Double psi;

    @Override
    public int compare(DataInput o1, DataInput o2) {
        if (o1.getTau() == o2.getTau()) {
            return 0;
        }
        return (o1.getTau() > o2.getTau()) ? 1 : -1;
    }

    public Double getPsi() {
        return psi;
    }

    public void setPsi(Double psi) {
        this.psi = psi;
    }
}
