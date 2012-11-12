/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.entidades.DataInput;


import java.awt.Color;
import java.util.ArrayList;

import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;
import tdc.Utilidades;
import tdc.entidades.FuncionTransferencia;
import tdc.gui.entidades.MyColorCellRenderer;
import tdc.segundo_orden.gui.EntradaEscalonOrdenDosForm;

/**
 *
 *
 * 2 decimales colores o referencia
 * @author fanky
 */
public class EntradaEscalon extends FuncionTransferencia {

    public static double NCTE_TAU_GRAFICA = 10D;
    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Escalón";
    private Double maxTau = 0D;
    private List<Double> psiList = new ArrayList<Double>();

    public EntradaEscalon(EntradaEscalonOrdenDosForm input) {
        super(input);
        this.psiList = input.getPsi();
        init();
    }

    private void init() {
        for (DataInput di : input_catalog) {
            if (maxTau < di.getTau()) {
                maxTau = di.getTau();
            }
        }
    }

    @Override
    protected void createDataset() {
        data = new XYSeriesCollection();
        colores = new ArrayList<Color>();
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                data.addSeries(getMainChart(di, psi));
                data.addSeries(getAmplitud(di));
            }
        }

    }

    //<editor-fold desc="overriden-methods">
    protected double getfdet(DataInput di, double time, Double psi) {
        Double result = 0D;
        //psi >0
        if (psi < 0) {
            throw new IllegalArgumentException("invalid psi value: " + psi);
        }
        if (psi == 1) {
            Double t1First = 1 + time / di.getTau();
            debug("t1First: " + t1First);
            Double t1Second = Math.pow(Math.E, (-time / di.getTau()));
            debug("t1Second: " + t1Second);
            
            result = 1 - t1First * t1Second;
            
        }
        else if (psi < 1) {
            Double t1First = 1 / (Math.sqrt(1 - Math.pow(psi, 2)));
            Double t1Second = Math.pow(Math.E, ((-psi * time) / di.getTau()));
            Double secondTerm1 = t1First * t1Second;
            debug("secondTerm1: " + secondTerm1);
            
            Double sinFirst = Math.sqrt(1 - Math.pow(psi, 2)) * time / di.getTau();
            Double sinSecond = Math.atan(Math.sqrt(1 - Math.pow(psi, 2)) / psi);
            Double secondTerm2 = Math.sin(sinFirst + sinSecond);
            debug("secondTerm2: " + secondTerm2);
            
            result = 1 - secondTerm1 * secondTerm2;
        }  else { //psi > 1 :P
            Double t1First = Math.pow(Math.E, ((-psi * time) / di.getTau()));
            debug("t1First: " + t1First);
            Double coshFirst = Math.cosh(Math.sqrt(Math.pow(psi, 2) - 1)* (time / di.getTau()));
            debug("coshFirst: " + coshFirst);
            Double t1Second = psi / (Math.sqrt(Math.pow(psi, 2) - 1));
            debug("t1Second: " + t1Second);
            Double sinhFirst = Math.sinh(Math.sqrt(Math.pow(psi, 2) - 1) * (time / di.getTau()));
            debug("sinhFirst: " + sinhFirst);
            debug("generado??: "+(t1First*(coshFirst + t1Second*sinhFirst)));
            result = 1 - t1First * (coshFirst + t1Second * sinhFirst);
        }
        //return di.getAmplitud() * result;
        debug("Y(" + time + ") generado: " + result);
        debug("-----------------");
        return result;

    }

    public double getPorcentajeAlgebraico(DataInput di) {
        return 2.2D * di.getTau();
    }

    public double getTiempoSubida(DataInput di) {
        debug("---------------TIEMPO DE SUBIDA--------------");
        double tmin = 0d;//al 10% de la amplitud
        double tmax = 0d;//al 90% de la amplitud
        //buscamos el tmin
        debug("--tmin seek");
        double ytmin = di.getAmplitud() * 0.1;
        double ytmax = di.getAmplitud() * 0.9;
        tmin = -di.getTau() * Math.log(-((ytmin / di.getAmplitud()) - 1));
        debug("tmin found! " + tmin);
        tmax = -di.getTau() * Math.log(-((ytmax / di.getAmplitud()) - 1));
        debug("tmax found! " + tmax);
        return tmax - tmin;
    }

    private XYSeries getMainChart(DataInput di) {
        throw new UnsupportedOperationException("not supported yet");
    }

    private XYSeries getMainChart(DataInput di, Double psi) {
        XYSeries reto = new XYSeries(di.getLabel() + " -- " + psi);
        debug("generating Graphic tau: " + di.getTau() + " amplitud: " + di.getAmplitud());
        debug("psi: " + psi);
        for (double time = 0; time < NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
            //valor de Y(t)
            double value = getfdet(di, time, psi);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getAmplitud(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel() + "Amplitud ");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, di.getAmplitud());
        reto.add(numberTau, di.getAmplitud());
        return reto;
    }

    //</editor-fold>
    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
                CHART_TITLE, // chart title
                "tiempo", // domain axis label
                "Y(t)", // range axis label
                data, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
                );
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        // set the color for each series
        for (int i = 0; i < colores.size(); i++) {
            renderer.setSeriesPaint(i, colores.get(i));
        }
        plot.setRenderer(renderer);
        //percentage (rangeAxis)
        final NumberAxis timeAxis = new NumberAxis("tiempo");
        timeAxis.setInverted(false);
        timeAxis.setRange(0.0, 5 * maxTau);
        timeAxis.setTickUnit(new NumberTickUnit(1));

        final NumberAxis functionAxis = new NumberAxis("y(t)");
        functionAxis.setInverted(false);
        functionAxis.setTickUnit(new NumberTickUnit(1));
        plot.setRangeAxis(functionAxis);


    }

    //TODO: change this, generate different table data.
    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel(new Object[]{"Categoria", "Tiempo Subida", "Tiempo Asentamiento"}, 0);
        for (DataInput di : input_catalog) {
            tmodel.addRow(new Object[]{di.getLabel(),
                        Utilidades.DECIMAL_FORMATTER.format(getPorcentajeAlgebraico(di)),
                        Utilidades.DECIMAL_FORMATTER.format(getTiempoAsentamiento(di))
                    });
        }
        return tmodel;
    }

    private Double getTiempoAsentamiento(DataInput di) {
        return DataInput.NCTE_TAU_TABLA * di.getTau();
    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new MyColorCellRenderer(input_catalog);
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}