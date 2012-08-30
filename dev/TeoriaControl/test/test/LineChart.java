/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author fanky
 */
public class LineChart extends JPanel {
//    private JFreeChart myChart;
    private static double[][] starts = new double[][]{{1d,2d},{3d,4d},{5d,6d}};
    private static double[][] ends = new double[][]{{3d,6d},{9d,12d},{15,18}};

    public static JFreeChart getChart() {
        XYSeries series = new XYSeries("XYGraph");

//        series.add(1, 1);
//        series.add(1, 2);
//        series.add(2, 1);
//        series.add(3, 9);
//        series.add(4, 10);
        for(double y=0D;y<10;y=y+0.01D ){
            if(y<4){
                series.add(y,5D);
            }else{
                series.add(y,0);
                break;
            }
        }
//        series.add(0, 5);
//        series.add(1, 5);
//        series.add(2, 5);
//        series.add(3, 5);
//        series.add(4, 0);
        //         Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
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
        return myChart;
    }
    public LineChart(){
        init();
    }
    private void init(){

    }

    private static ChartPanel getMyChart(){
        DefaultIntervalCategoryDataset data = new DefaultIntervalCategoryDataset(starts,ends);
        JFreeChart myChart = ChartFactory.createLineChart("UnTitulo","Left here","Up axis here", data, PlotOrientation.VERTICAL, true, true, true);
        ChartUtilities.applyCurrentTheme(myChart);
        ChartPanel localChartPanel = new ChartPanel(myChart, true);
        localChartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        return localChartPanel;
    }

    public static void main(String args[]){
        JFrame frmMain = new JFrame("TeoriaDeControl v0.02");
        frmMain.getContentPane().add(new tdc.entidades.XYChart(getChart()));
        frmMain.setLocationRelativeTo(null);
        frmMain.pack();
        frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setVisible(true);
    }
    public static ChartPanel xyseries(){
//        JFreeChart myChart = null;
        //         Create a simple XY chart
        XYSeries series = new XYSeries("XYGraph");

//        series.add(1, 1);
//        series.add(1, 2);
//        series.add(2, 1);
//        series.add(3, 9);
//        series.add(4, 10);
        for(double y=0D;y<10;y=y+0.01D ){
            if(y<4){
                series.add(y,5D);
            }else{
                series.add(y,0);
                break;
            }
        }
//        series.add(0, 5);
//        series.add(1, 5);
//        series.add(2, 5);
//        series.add(3, 5);
//        series.add(4, 0);
        //         Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
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
