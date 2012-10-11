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

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import tdc.Utilidades;
import tdc.entidades.FuncionTransferencia;
import tdc.gui.entidades.MyColorCellRenderer;
import tdc.util.ApplicationConstants;

/**
 *
 *
 * 2 decimales colores o referencia
 * @author fanky
 */
public class EntradaEscalon extends FuncionTransferencia {
    public static String CHART_TITLE = "Respuesta Transiente Sistema Primer orden: Entrada tipo Escalon";
    private Double maxTau = 0D;
    private Boolean dibujarAmplitud = true;

    public EntradaEscalon(DataInputCatalog input, Boolean dibujarAmplitud) {
        super(input);
        this.dibujarAmplitud = dibujarAmplitud;
        init();
    }

    public EntradaEscalon(DataInputCatalog input) {
        this(input, true);
    }

    private void init() {
        for (DataInput di : input_catalog) {
            if (maxTau < di.getTau()) {
                maxTau = di.getTau();
            }
        }
    }

    //<editor-fold desc="overriden-methods">
    protected double getfdet(DataInput di, double time) {
        return (di.getAmplitud() * (1 - Math.pow(Math.E, (-time / di.getTau()))));
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
        tmin = -di.getTau() * Math.log(-((ytmin / di.getAmplitud()) - 1));//-(tau * (Math.log(-((ytmin-1)/amplitud))));
        debug("tmin found! " + tmin);
        tmax = -di.getTau() * Math.log(-((ytmax / di.getAmplitud()) - 1));
        debug("tmax found! " + tmax);
        return tmax - tmin;
    }

        private XYSeries getMainChart(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel());
        debug("generating Graphic tau: " + di.getTau() + " amplitud: " + di.getAmplitud());
        for (double time = 0; time < DataInput.NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
            //valor de Y(t)
            double value = getfdet(di, time);
            debug("Y(" + time + ") generado: " + value);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getCteTiempo(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel() + " 1" + ApplicationConstants.UNICODE_TAU);
        double value = getfdet(di, di.getTau());
        reto.add(0, value);
        reto.add(di.getTau(), value);
        reto.add(di.getTau(), 0);
        return reto;
    }

    private XYSeries getAmplitud(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel() + "Amplitud ");
        Number numberTau = DataInput.NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, di.getAmplitud());
        reto.add(numberTau, di.getAmplitud());
        return reto;
    }

    //</editor-fold>
    @Override
    protected void createDataset() {
        data = new XYSeriesCollection();
        colores = new ArrayList<Color>();
        for (DataInput di : input_catalog) {
            data.addSeries(getMainChart(di));
            colores.add(di.getColor());
            data.addSeries(getCteTiempo(di));
            colores.add(Color.black);
            if (dibujarAmplitud) {
                data.addSeries(getAmplitud(di));
                colores.add(Color.black);
            }
        }

    }

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
//        functionAxis.setRange(0.0, 5 * maxTau);//automatic
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
}