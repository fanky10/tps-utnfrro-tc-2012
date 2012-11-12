/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.entidades;

import java.awt.Color;
import java.awt.Stroke;

/**
 *
 * @author Fanky10 <fanky10@gmail.com>
 */
public class Linea {
    private Color color;
    private Stroke stroke;
    
    public Linea(Color color,Stroke stroke){
        this.color = color;
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
    
    
}
