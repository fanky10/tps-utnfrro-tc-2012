/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import java.awt.BasicStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.entidades.DataInput;


import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import tdc.entidades.Linea;
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
    private Map<XYSeries, Linea> lineasMap = new LinkedHashMap<XYSeries, Linea>();

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
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                data.addSeries(getMainChart(di, psi));

            }
        }
        // el parametro de amplitud con otro tipo de stroke (:
        Stroke dashedStroke = new BasicStroke(
                1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[]{6.0f, 6.0f}, 0.0f);
        Linea linea = new Linea(Color.black, dashedStroke);

        XYSeries serie = getAmplitud(input_catalog.get(0));
        data.addSeries(serie);
        lineasMap.put(serie, linea);

        serie = getBandaSuperior(input_catalog.get(0));
        data.addSeries(serie);
        lineasMap.put(serie, linea);

        serie = getBandaInferior(input_catalog.get(0));
        data.addSeries(serie);
        lineasMap.put(serie, linea);

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

        } else if (psi < 1) {
            Double t1First = 1 / (Math.sqrt(1 - Math.pow(psi, 2)));
            Double t1Second = Math.pow(Math.E, ((-psi * time) / di.getTau()));
            Double secondTerm1 = t1First * t1Second;
            debug("secondTerm1: " + secondTerm1);

            Double sinFirst = Math.sqrt(1 - Math.pow(psi, 2)) * time / di.getTau();
            Double sinSecond = Math.atan(Math.sqrt(1 - Math.pow(psi, 2)) / psi);
            Double secondTerm2 = Math.sin(sinFirst + sinSecond);
            debug("secondTerm2: " + secondTerm2);

            result = 1 - secondTerm1 * secondTerm2;
        } else { //psi > 1 :P
            Double t1First = Math.pow(Math.E, ((-psi * time) / di.getTau()));
            debug("t1First: " + t1First);
            Double coshFirst = Math.cosh(Math.sqrt(Math.pow(psi, 2) - 1) * (time / di.getTau()));
            debug("coshFirst: " + coshFirst);
            Double t1Second = psi / (Math.sqrt(Math.pow(psi, 2) - 1));
            debug("t1Second: " + t1Second);
            Double sinhFirst = Math.sinh(Math.sqrt(Math.pow(psi, 2) - 1) * (time / di.getTau()));
            debug("sinhFirst: " + sinhFirst);
            debug("generado??: " + (t1First * (coshFirst + t1Second * sinhFirst)));
            result = 1 - t1First * (coshFirst + t1Second * sinhFirst);
        }

        debug("Y(" + time + ") generado: " + (di.getAmplitud() * result));
        debug("-----------------");
        return di.getAmplitud() * result;

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
        XYSeries reto = new XYSeries("Amplitud");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, di.getAmplitud());
        reto.add(numberTau, di.getAmplitud());
        return reto;
    }

    private XYSeries getBandaSuperior(DataInput di) {
        Double banda = di.getAmplitud() * (1 + 0.05);
        XYSeries reto = new XYSeries("Banda superior");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, banda);
        reto.add(numberTau, banda);
        return reto;
    }

    private XYSeries getBandaInferior(DataInput di) {
        Double banda = di.getAmplitud() * (1 - 0.05);
        XYSeries reto = new XYSeries("Banda inferior");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, banda);
        reto.add(numberTau, banda);
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
        for (XYSeries key : lineasMap.keySet()) {
            int idx = data.indexOf(key);
            renderer.setSeriesPaint(idx, lineasMap.get(key).getColor());
            renderer.setSeriesStroke(idx, lineasMap.get(key).getStroke());
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
        DefaultTableModel tmodel = new DefaultTableModel(new String[]{"Sin suficientes datos"}, 0);
        List<String> header = new ArrayList<String>();
        if (input_catalog.size() == 1) {
            header.add("Terminos");
            for (Double psi : psiList) {
                header.add("psi " + psi);
            }
            tmodel = new DefaultTableModel(header.toArray(new String[0]), 0);
            Map<String, List<String>> terminos = getTerminos(input_catalog.get(0).getTau(), psiList);
            for (String key : terminos.keySet()) {
                List<String> values = terminos.get(key);
                List<String> row = new ArrayList<String>();
                row.add(key.toString());
                row.addAll(values);
                tmodel.addRow(row.toArray(new String[0]));
            }
        } else {//several tau's 1 psi
        }

        return tmodel;
    }

    private Map<String, List<String>> getTerminos(Double tau, List<Double> psiList) {
        Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
        List<String> overshootValues = new ArrayList<String>();
        List<String> decayRatioValues = new ArrayList<String>();
        for (Double psi : psiList) {
            //add overshoot
            if (psi < 1) {
                Double overshoot = getOvershoot(psi);
                overshootValues.add(Utilidades.DECIMAL_FORMATTER.format(overshoot));
                decayRatioValues.add(Utilidades.DECIMAL_FORMATTER.format(getDecayRatio(overshoot)));
            } else {
                overshootValues.add("-");
                decayRatioValues.add("-");
            }
        }
        result.put("Overshoot", overshootValues);
        result.put("DecayRatio", decayRatioValues);
        return result;
    }

    private Double getOvershoot(Double psi) {
        Double exp = -(Math.PI * psi) / (Math.sqrt(1 - Math.pow(psi, 2)));
        return Math.pow(Math.E, exp);

    }

    private Double getDecayRatio(Double overshoot) {
        return Math.pow(overshoot, 2);
    }

    private Double getRiseTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Double getResponseTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Double getPeriodOscilation() {
        throw new UnsupportedOperationException("Not supported yet.");
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