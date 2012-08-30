/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmTest.java
 *
 * Created on 27-abr-2011, 21:05:46
 */

package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author fanky
 */
public class frmTest extends javax.swing.JFrame {
    private JFreeChart chart;
    /** Creates new form frmTest */
    public frmTest() {
        initComponents();
        chart = LineChart.getChart();
        init();
    }
    private void init(){
        XYChart hola = new XYChart(chart);
        getContentPane().add(new tdc.entidades.XYChart(chart),java.awt.BorderLayout.CENTER);
    }
    private void updPlot(int max){

        XYSeries series = new XYSeries("XYGraph");
        for(double i=0;i<max;i++){
            series.add(i,Math.pow(i, 2));
        }
        XYSeriesCollection coll = new XYSeriesCollection(series);
        XYPlot p = chart.getXYPlot();
        p.setDataset(coll);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frmTest().setVisible(true);
//            }
//        });
//    }


    public static void main(String args[]){
        final JFreeChart chart = LineChart.getChart();
        JFrame frmMain = new JFrame("TeoriaDeControl v0.02");
        frmMain.getContentPane().add(new tdc.entidades.XYChart(chart));
        JButton btn1 = new JButton("Click Me");
        btn1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                XYSeries series = new XYSeries("XYGraph");
                double max = 50;
                for(double i=0;i<max;i+=0.1D){
                    series.add(i,Math.pow(i, 2));
                }
                XYSeriesCollection coll = new XYSeriesCollection(series);
                
                //se vuelven a setear los axis
                NumberAxis domainAxis = new NumberAxis("Dominio");
                NumberAxis rangeAxis = new NumberAxis("Rango");
                domainAxis.setRange(0, 2.5D);
                rangeAxis.setRange(0, 10);
                //update plot(Plot)
                XYPlot p = chart.getXYPlot();
                p.setDomainAxis(domainAxis);
                p.setRangeAxis(rangeAxis);
                p.setDataset(coll);
            }
        });
        frmMain.getContentPane().add(btn1,java.awt.BorderLayout.SOUTH);
        frmMain.setLocationRelativeTo(null);
        frmMain.pack();
        frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
