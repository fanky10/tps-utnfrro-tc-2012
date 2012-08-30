/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.primer_orden.entidades;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.entidades.DataInput;
import tdc.entidades.DataInputCatalog;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import tdc.Utilidades;
import tdc.entidades.FuncionTransferencia;
import tdc.gui.entidades.MyColorCellRenderer;



/**
 *
 *
 * 2 decimales colores o referencia
 * @author fanky
 */
public class EntradaEscalon extends FuncionTransferencia {
    public EntradaEscalon(DataInputCatalog input) {
        super(input);
    }
    
    //<editor-fold desc="overriden-methods">
    protected double getfdet(DataInput di, double time){
        return (di.getAmplitud()*(1-Math.pow(Math.E,(-time/di.getTau()))));
    }
    public double getPorcentajeAlgebraico(DataInput di){
        return 2.2D*di.getTau();
    }
    public double getTiempoSubida(DataInput di){
        debug("---------------TIEMPO DE SUBIDA--------------");
        double tmin = 0d;//al 10% de la amplitud
        double tmax = 0d;//al 90% de la amplitud
        //buscamos el tmin
        debug("--tmin seek");
        double ytmin = di.getAmplitud() * 0.1;
        double ytmax = di.getAmplitud() * 0.9;
        tmin = -di.getTau() * Math.log(-((ytmin/di.getAmplitud())-1));//-(tau * (Math.log(-((ytmin-1)/amplitud))));
        debug("tmin found! "+tmin);
        tmax = -di.getTau() * Math.log(-((ytmax/di.getAmplitud())-1));
        debug("tmax found! "+tmax);
        return tmax-tmin;
    }

    private XYSeries getMainChart(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel());
        debug("generating Graphic tau: "+di.getTau()+" amplitud: "+di.getAmplitud());
        for(double time =0;time<DataInput.N_TAU*DataInput.TAU_MAX;time=time+DataInput.JUMP){
            //valor de Y(t)
            double value =getfdet(di, time);
            debug("Y("+time+") generado: "+value);
            reto.add(time,value);
        }
        return reto;
    }
    private XYSeries getCteTiempo(DataInput di){

        XYSeries reto = new XYSeries(di.getLabel()+"1tau");
        //el valor de y(t) cuando t=1tau
        double value = getfdet(di,di.getTau());
//        debug("============================================");
//        debug("generating cte tiempo "+value);
        reto.add(0,value);
        reto.add(di.getTau(),value);
        reto.add(di.getTau(),0);
        return reto;
    }

    //</editor-fold>
    
    @Override
    protected void createDataset() {
        data = new XYSeriesCollection();
        colores = new ArrayList<Color>();
        for(DataInput di: input_catalog){
            data.addSeries(getMainChart(di));
            colores.add(di.getColor());
            data.addSeries(getCteTiempo(di));
            colores.add(Color.black);
        }
        
    }

    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
            "Entrada Escalon",      // chart title
            "tiempo",                   // domain axis label
            "Y(t)",                  // range axis label
            data,                     // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        // set the color for each series
        for(int i=0;i<colores.size();i++){
            renderer.setSeriesPaint(i, colores.get(i));
        }
        plot.setRenderer(renderer);
        
    }
    
    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel(new Object[]{"Categoria","Tiempo Subida Grafica","Tiempo Subida Algebraico (2.2*Tau)" }, 0);
        for(DataInput di: input_catalog){
            tmodel.addRow(new Object[]{di.getLabel(),
                Utilidades.DECIMAL_FORMATTER.format(getTiempoSubida(di)),
                Utilidades.DECIMAL_FORMATTER.format(getPorcentajeAlgebraico(di))
            }); 
        }
        return tmodel;
    }

//    @Override
//    protected double getfdet(java.io.DataInput di, double time) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new MyColorCellRenderer(input_catalog);
    }


}