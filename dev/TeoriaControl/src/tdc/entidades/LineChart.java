/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.datos.SeriesGenerator;
import tdc.datos.ChartGenerator;

/**
 *
 * @author fanky
 */
public class LineChart extends JPanel{
    private XYSeriesCollection series_collection;
    private XYPlot plotter;
    private String x_axis_label;
    private String y_axis_label;
    private String title;
    public LineChart(String title){
        this(title,"x_axis", "y_axis");
    }
    public LineChart(String title,String x_axis_label,String y_axis_label){
        super(new BorderLayout());
        this.title=title;
        this.x_axis_label=x_axis_label;
        this.y_axis_label=y_axis_label;
        this.series_collection = new XYSeriesCollection();        
        init();
    }
    private void init(){
        //         Generate the graph
        JFreeChart myChart = ChartFactory.createXYLineChart(title, // Title
                x_axis_label, // x-axis Label
                y_axis_label, // y-axis Label
                series_collection, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
            );

        //new code: 2011-04-27
        plotter = myChart.getXYPlot();
        NumberAxis domainAxis = new NumberAxis(x_axis_label);
        NumberAxis rangeAxis = new NumberAxis(y_axis_label);
        plotter.setDomainAxis(domainAxis);
        plotter.setRangeAxis(rangeAxis);
        myChart.setBackgroundPaint(Color.white);
        plotter.setOutlinePaint(Color.black);
        //end new code
        ChartPanel localChartPanel = new ChartPanel(myChart, true);
        localChartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        add(localChartPanel);
    }
    public void setGeneradores(ChartGenerator data){
        series_collection.removeAllSeries();
        for(SeriesGenerator dg: data){
            for(XYSeries s: dg.getXy_series_collection()){
                series_collection.addSeries(s);
            }
//            series_collection.addSeries(dg.getXy_series_collection());
//            series_collection.addSeries(dg.getCteTiempo());//le agrego el mini grafico
        }

    }
    public void addSeries(){

    }
    public static ChartPanel xyseries(ChartGenerator genV){
//        JFreeChart myChart = null;
        //         Create a simple XY chart
//        XYSeries series = new XYSeries("XYGraph");
//        series.add(1, 1);
//        series.add(1, 2);
//        series.add(2, 1);
//        series.add(3, 9);
//        series.add(4, 10);
        //         Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.removeAllSeries();
//        for(SeriesGenerator g: genV){
//            dataset.addSeries(g.getXy_series_collection());
//        }


        //         Generate the graph
        JFreeChart myChart = ChartFactory.createXYLineChart("XY Chart", // Title
                "x-axis", // x-axis Label
                "y-axis", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
            );
        ChartPanel localChartPanel = new ChartPanel(myChart, true);
        localChartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        return localChartPanel;
    }
}
