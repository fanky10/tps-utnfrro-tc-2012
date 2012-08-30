/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.gui;

import javax.swing.JPanel;
import tdc.datos.ChartGenerator;
import tdc.entidades.FuncionTransferencia;
import tdc.entidades.LineChart;

/**
 *
 * @author fanky
 */
public abstract class pnlEntrada extends JPanel {
//    protected LineChart chart;
    protected ChartGenerator chart_gen;
    public pnlEntrada(String title, String x_axis,String y_axis){
//        chart = new LineChart(title,x_axis,y_axis);
        chart_gen = new ChartGenerator(title,x_axis,y_axis);
    }
    public abstract String toString();
    protected abstract void ingresar_datos();
    protected abstract void ver_tabla();
    /**
     * actualiza el grafico no importa como se cree el generador
     * @param gen
     */
    protected abstract void actualiza_grafico(ChartGenerator gen);
}
