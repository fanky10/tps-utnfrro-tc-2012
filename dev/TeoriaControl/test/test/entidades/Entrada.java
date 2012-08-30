/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.entidades;


import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.entidades.DataInput;
import tdc.entidades.DataInputCatalog;

/**
 *
 * @author fanky
 */
public abstract class Entrada {
    protected DataInputCatalog input_catalog;
    protected String title;
    protected XYSeriesCollection data;
    protected JFreeChart chart;
    protected ArrayList<Color> colores;
    
    public Entrada(DataInputCatalog input) {
        this.input_catalog = input;
    }
    
    protected abstract void createDataset();
    protected abstract void createChart();
    public abstract DefaultTableModel createTableModel();
    
    protected abstract double getfdet(DataInput di, double time);

    public DataInputCatalog getInput_catalog() {
        return input_catalog;
    }

    public void setInput_catalog(DataInputCatalog input_catalog) {
        this.input_catalog = input_catalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    //<editor-fold desc="debug">
    public static boolean DEBUG=true;
    protected static void debug(String text){
        if(DEBUG){
            System.out.println("DEBUG: "+text);
        }
    }

    //</editor-fold>

}
