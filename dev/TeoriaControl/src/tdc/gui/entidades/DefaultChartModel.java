/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.gui.entidades;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.entidades.XYChart;

/**
 *
 * @author facundo
 */
public class DefaultChartModel implements ChartModel{
    private static final String X_AXIS_LABEL = "X Axis";
    private static final String Y_AXIS_LABEL = "Y Axis";
    private static final String DEFAULT_TITLE = "My title";
    private String title;
    protected NumberAxis domainAxis;
    protected NumberAxis rangeAxis;
    private XYSeriesCollection data;
    private XYChart chart_panel;
    
    public DefaultChartModel(JFreeChart chart){
        this.chart_panel = new XYChart(chart);
    }
    public DefaultChartModel(){
        this(DEFAULT_TITLE, new NumberAxis(X_AXIS_LABEL), new NumberAxis(Y_AXIS_LABEL),new XYSeriesCollection());
    }
    public DefaultChartModel(String title, String x_axis_label, String y_axis_label){
        this(title, new NumberAxis(x_axis_label), new NumberAxis(y_axis_label),new XYSeriesCollection());
    }
    public DefaultChartModel(String title,NumberAxis domainAxis, NumberAxis rangeAxis,XYSeriesCollection data){
        this.title=title;
        this.domainAxis=domainAxis;
        this.rangeAxis=rangeAxis;
        this.data=data;
        createChartPanel();
    }
    private void createChartPanel(){
        //         Generate the graph
        JFreeChart single_chart = ChartFactory.createXYLineChart(title, // Title
                domainAxis.getLabel(), // x-axis Label
                rangeAxis.getLabel(), // y-axis Label
                data, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
            );
        chart_panel = new XYChart(single_chart);
    }

    public JPanel getPanelActual() {
        return chart_panel;
    }
    
}
