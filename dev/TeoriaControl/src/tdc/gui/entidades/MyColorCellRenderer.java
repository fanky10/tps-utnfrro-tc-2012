/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.gui.entidades;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import tdc.entidades.DataInput;
import tdc.entidades.DataInputCatalog;

/**
 *
 * @author facundo
 */
public class MyColorCellRenderer implements TableCellRenderer{
    private static final DefaultTableCellRenderer DEFAULT_CELL_RENDERER = new DefaultTableCellRenderer();
    private List<Color> colores = new ArrayList<Color>();
    public MyColorCellRenderer(DataInputCatalog data_input){
        if(data_input.size()>1){
            for(DataInput di: data_input){
                colores.add(di.getColor());
            }
        }
        
    }
    public MyColorCellRenderer(List<Color> colores){
        this.colores = colores;
    }
    

    public Component getTableCellRendererComponent(JTable jtable, Object value,boolean isSelected, boolean hasFocus, int row, int column)  {
        if(isSelected){
            return DEFAULT_CELL_RENDERER.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
        }
        Component renderer = DEFAULT_CELL_RENDERER.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
        ((JLabel) renderer).setOpaque(true);
        try{
            Color c = colores.get(row);
            renderer.setBackground(c);
            renderer.setForeground(Color.BLACK);
            return renderer;
        }catch(IndexOutOfBoundsException t){
            System.out.println("imput with no data: "+t.getMessage());
            return DEFAULT_CELL_RENDERER.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
        }
    }
}
