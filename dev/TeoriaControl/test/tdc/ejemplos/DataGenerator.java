/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.ejemplos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author fanky
 */
public class DataGenerator
//    implements ActionListener
  {
    private Timer time;
    DataGenerator(int arg2, ActionListener al)
    {
        time = new Timer(arg2,al);
    }

    public void start(){
        time.start();
    }

    
  }