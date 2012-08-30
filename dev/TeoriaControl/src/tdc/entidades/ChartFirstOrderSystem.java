/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import tdc.datos.SeriesGenerator;
import tdc.datos.ChartGenerator;

/**
 *
 * @author fanky
 * @deprecated unused
 */
public class ChartFirstOrderSystem extends JPanel{

//    private TimeSeries total = new TimeSeries("Y(t)");
//    ChartGenerator generadores = new ChartGenerator();
//    TimeSeriesCollection localTimeSeriesCollection;
//
//    public ChartFirstOrderSystem(){
//        super(new BorderLayout());
//        this.localTimeSeriesCollection = new TimeSeriesCollection();
//        init();
//    }
//    public ChartFirstOrderSystem(ChartGenerator generadores){
//        super(new BorderLayout());
//        this.generadores = generadores;
//        this.localTimeSeriesCollection = new TimeSeriesCollection();
//        init();
//    }
//    private void init(){
//        addSeries();
//
//        DateAxis localDateAxis = new DateAxis("Tiempo");
//        NumberAxis localNumberAxis = new NumberAxis("Y(t)");
//        localDateAxis.setTickLabelFont(new Font("SansSerif", 0, 12));
//        localNumberAxis.setTickLabelFont(new Font("SansSerif", 0, 12));
//        localDateAxis.setLabelFont(new Font("SansSerif", 0, 14));
//        localNumberAxis.setLabelFont(new Font("SansSerif", 0, 14));
//
//        XYLineAndShapeRenderer localXYLineAndShapeRenderer = new XYLineAndShapeRenderer(true, false);
//        localXYLineAndShapeRenderer.setSeriesPaint(0, Color.red);
//        localXYLineAndShapeRenderer.setSeriesPaint(1, Color.green);
//        localXYLineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(3.0F, 0, 2));
//        localXYLineAndShapeRenderer.setSeriesStroke(1, new BasicStroke(3.0F, 0, 2));
//        XYPlot localXYPlot = new XYPlot(localTimeSeriesCollection, localDateAxis, localNumberAxis, localXYLineAndShapeRenderer);
//        localDateAxis.setAutoRange(true);
//        localDateAxis.setLowerMargin(0.0D);
//        localDateAxis.setUpperMargin(0.0D);
//        localDateAxis.setTickLabelsVisible(true);
//        localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        JFreeChart localJFreeChart = new JFreeChart("Teoria de Control", new Font("SansSerif", 1, 24), localXYPlot, true);
//        ChartUtilities.applyCurrentTheme(localJFreeChart);
//        ChartPanel localChartPanel = new ChartPanel(localJFreeChart, true);
//        localChartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
//        add(localChartPanel);
//    }
//    public void addGeneradores(ChartGenerator data){
//        localTimeSeriesCollection.removeAllSeries();
//        generadores = data;
//        addSeries();
//    }
//    private void addSeries(){
//        if(generadores == null){
//            generadores = new ChartGenerator();
//        }
//        if(generadores.isEmpty()){
//            return;
////            generadores.add(new SeriesGenerator("Y(t)1"));
//        }
//        for(SeriesGenerator gen: generadores){
//            localTimeSeriesCollection.addSeries(gen.getTime_series());
//        }
//    }
}
