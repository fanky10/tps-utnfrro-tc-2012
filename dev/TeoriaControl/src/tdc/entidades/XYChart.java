/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author fanky
 */
public class XYChart extends JPanel{
    private JFreeChart chart;
    public XYChart(JFreeChart chart){
        super(new BorderLayout());
        this.chart=chart;
        init();
    }
    private void init(){
        ChartPanel localChartPanel = new ChartPanel(chart, true);
        localChartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createLineBorder(Color.black)));
        add(localChartPanel);
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }

}
