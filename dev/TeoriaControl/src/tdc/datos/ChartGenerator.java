/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import tdc.entidades.XYChart;

/**
 *
 * @author fanky
 */
public class ChartGenerator extends ArrayList<SeriesGenerator> implements Comparator<SeriesGenerator>{
    private static final String X_AXIS_LABEL = "X Axis";
    private static final String Y_AXIS_LABEL = "Y Axis";
    private static final String DEFAULT_TITLE = "My title";
    private String title;
    protected NumberAxis domainAxis;
    protected NumberAxis rangeAxis;
    public ChartGenerator(){
        this(DEFAULT_TITLE,X_AXIS_LABEL,Y_AXIS_LABEL);
    }
    public ChartGenerator(String title, String x_axis_label, String y_axis_label){
        this(title, new NumberAxis(x_axis_label), new NumberAxis(y_axis_label));
    };
    public ChartGenerator(String title,NumberAxis domainAxis, NumberAxis rangeAxis){
        super();
        this.title=title;
        this.domainAxis=domainAxis;
        this.rangeAxis=rangeAxis;
    }
    //orden descendiente :D
    public int compare(SeriesGenerator o1, SeriesGenerator o2) {
        if(o1.getTau()==o2.getTau()){
            return 0;
        }
        return (o1.getTau()>o2.getTau())?1:-1;
    }

    public DefaultTableModel getInforme() {
        DefaultTableModel tmodel = new DefaultTableModel();
        tmodel.setColumnIdentifiers(new Object[]{"Categoria","Tiempo Subida Grafica","Tiempo Subida Algebraico (2.2*Tau)" });
        for(SeriesGenerator dg: this){
            tmodel.addRow(new Object[]{dg.toString(),dg.getTiempoSubida(),dg.getPorcentajeAlgebraico()});
        }
        return tmodel;
    }
    private XYChart single_chart_panel;
    public XYChart getXYChart(){
        if(single_chart_panel == null){
            debug("creating chart..for: "+title);
            createChartPanel(title,domainAxis.getLabel(),rangeAxis.getLabel());
        }

        JFreeChart local_chart = single_chart_panel.getChart();
        XYPlot plotter = local_chart.getXYPlot();
        debug("modifying chart (setAxis)");
        //update plot
        //se vuelven a setear los axis
        plotter.setDomainAxis(domainAxis);
        plotter.setRangeAxis(rangeAxis);
        debug("modifying chart (addSeriesColl)");
        org.jfree.data.xy.XYSeriesCollection series_collection = (org.jfree.data.xy.XYSeriesCollection) plotter.getDataset();
        debug("mysize: "+this.size());
        for(SeriesGenerator dg: this){
            for(XYSeries s: dg.getXy_series_collection()){
                series_collection.addSeries(s);
            }
        }
        local_chart.setBackgroundPaint(Color.white);
        plotter.setOutlinePaint(Color.black);
        return single_chart_panel;
    }
    private void createChartPanel(String title, String x_label, String y_label){
        org.jfree.data.xy.XYSeriesCollection series_collection = new org.jfree.data.xy.XYSeriesCollection();
        //         Generate the graph
        JFreeChart single_chart = ChartFactory.createXYLineChart(title, // Title
                x_label, // x-axis Label
                y_label, // y-axis Label
                series_collection, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
            );
        single_chart_panel = new XYChart(single_chart);
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
