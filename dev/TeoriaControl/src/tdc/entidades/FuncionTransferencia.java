/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author fanky
 * v0.1 inicial
 * v0.2 usa plot
 * v0.3 cambia el concepto totalmente
 * 
 */
public abstract class FuncionTransferencia {
    protected DataInputCatalog input_catalog;
    protected String title;
    protected XYSeriesCollection data;
    protected JFreeChart chart;
    protected ArrayList<Color> colores;
    
    public FuncionTransferencia(DataInputCatalog input) {
        this.input_catalog = input;
        this.data = new XYSeriesCollection();
        this.colores = new ArrayList<Color>();
    }
    
    protected abstract void createDataset();
    protected abstract void createChart();
    public abstract DefaultTableModel createTableModel(); 
    public abstract TableCellRenderer createTableRenderer();
    protected abstract double getfdet(DataInput di, double time);
    
    public JFreeChart getChart(){
        createDataset();
        createChart();
        return chart;
    }

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
