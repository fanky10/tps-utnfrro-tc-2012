/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;

import java.text.DecimalFormat;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author fanky
 */
public abstract class SeriesGenerator{

    
    public static double A = 5D;
    public static double TAU = 6D;
    public static double TAU_MAX = 10D;
    public static double N_TAU = 5.5D;
    public static double JUMP = 0.03D;
    
//    private static final int MAX_ITEM_AGE=(30*1000);

    protected XYSeriesCollection xy_series_collection;
    protected double amplitud = A;
    protected double tau = TAU;
    protected String label;
    protected SeriesGenerator(String label){
        this(label, A,TAU);
    }
    protected SeriesGenerator(String label,double a, double tau){
        this.label=label;
        this.amplitud = a;
        this.tau=tau;
        this.xy_series_collection = new XYSeriesCollection();
//        xy_series_collection = new XYSeries(label);
    }
    public String toString(){
        return label;
    }
    //<editor-fold desc="abstract-methods">
    protected abstract double getfdet(double time);
    public abstract double getPorcentajeTabla();
    public abstract void generate();
    public abstract double getPorcentajeAlgebraico();
    public abstract double getTiempoSubida();
//    public abstract XYSeries getCteTiempo();
    //</editor-fold>

    protected static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d).replace(',', '.'));
    }
    

    //<editor-fold desc="getters-setters">
    public XYSeriesCollection getXy_series_collection() {
        return xy_series_collection;
    }

    public void setXy_series_collection(XYSeriesCollection xy_series) {
        this.xy_series_collection = xy_series;
    }
    public double getAmplitud() {
        return amplitud;
    }

    public void setAmplitud(double amplitud) {
        this.amplitud = amplitud;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    //</editor-fold>


    //<editor-fold desc="debug">
    public static boolean DEBUG=true;
    protected static void debug(String text){
        if(DEBUG){
            System.out.println("DEBUG: "+text);
        }
    }
    //</editor-fold>

}
