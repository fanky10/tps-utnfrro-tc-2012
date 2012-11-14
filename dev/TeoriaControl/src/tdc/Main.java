/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc;

import tdc.entidades.FuncionTransferencia;
import tdc.gui.frmMain;

/**
 *
 * @author fanky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(String a: args){
            if(a.startsWith("debug=")){
                FuncionTransferencia.DEBUG = a.substring("debug=".length()).equals("1");
            }
        }
        new frmMain().setVisible(true);
    }

}
