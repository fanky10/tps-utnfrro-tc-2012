/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc;

import java.text.DecimalFormat;
import javax.swing.JTextField;

/**
 *
 * @author fanky
 */
public class Utilidades {
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("###.##");
    public static double getDouble(JTextField txt){
        try{
            double dd = Double.parseDouble(txt.getText());
            return dd;
        }catch(NumberFormatException ex){
            System.out.println("what the hell happened! "+ex.getMessage());
            txt.grabFocus();

        }
        return -1;
    }
    public static void setDouble(JTextField txt, double dd){
        txt.setText(String.valueOf(dd));
    }
    public static void setDouble(JTextField txt){
        double gen = 0D;
        do{
            gen = Math.random()*10;
        }while(gen<=5D);
        txt.setText(String.valueOf(gen));
    }
//    String output = myFormatter.format(value);
//    System.out.println(value + " " + pattern + " " + output);
}
