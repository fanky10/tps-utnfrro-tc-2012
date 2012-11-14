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
import java.awt.Paint;
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
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;
import tdc.Utilidades;
import tdc.entidades.FuncionTransferencia;
import tdc.entidades.Linea;
import tdc.gui.entidades.MyColorCellRenderer;
import tdc.segundo_orden.gui.EntradaEscalonImpulsoOrdenDosForm;
import tdc.segundo_orden.gui.pnlEntradaEscalon;

/**
 *
 *
 * 2 decimales colores o referencia
 * @author fanky
 */
public class EntradaEscalon extends FuncionTransferencia {

    private static final double NCTE_TAU_GRAFICA = 20D;
    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Escalón";
    private Double maxTau = 0D;
    private Double maxTime = 0D;
    private List<Double> psiList = new ArrayList<Double>();
    private Double porcAsentamiento = 0D;
    private Map<XYSeries, Linea> lineasMap = new LinkedHashMap<XYSeries, Linea>();
    //esto es para setear luego los colores que le pone el plotter, es re cabeza
    private List<XYSeries> seriesSinColor = new ArrayList<XYSeries>();
    
    public EntradaEscalon(EntradaEscalonImpulsoOrdenDosForm input) {
        super(input);
        this.psiList = input.getPsi();
        this.porcAsentamiento = input.getPorcentajeAsentamiento();
        init();
    }

    private void init() {
        for (DataInput di : input_catalog) {
            if (maxTau < di.getTau()) {
                maxTau = di.getTau();
            }
        }
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                double asentamiento = getTiempoAsentamiento(porcAsentamiento, psi, di);
                maxTime = (asentamiento>maxTime?asentamiento:maxTime);//lo sobreescribo si es mayor
            }
        }
        maxTime=maxTime*1.5;//un 50% mas.
    }

    @Override
    protected void createDataset() {
        data = new XYSeriesCollection();
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                XYSeries xys = getMainChart(di, psi);
                data.addSeries(xys);
                seriesSinColor.add(xys);
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
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            //valor de Y(t)
            double value = getfdet(di, time, psi);
            reto.add(time, value);
        }
        return reto;
    }

    
    private XYSeries getAmplitud(DataInput di) {
        XYSeries reto = new XYSeries("Amplitud");
        Number numberTau = maxTime;
        reto.add(0, di.getAmplitud());
        reto.add(numberTau, di.getAmplitud());
        return reto;
    }

    private XYSeries getBandaSuperior(DataInput di) {
        Double banda = di.getAmplitud() * (1 + porcAsentamiento / 100);
        XYSeries reto = new XYSeries("Banda superior");
        Number numberTau = maxTime;
        reto.add(0, banda);
        reto.add(numberTau, banda);
        return reto;
    }

    private XYSeries getBandaInferior(DataInput di) {
        Double banda = di.getAmplitud() * (1 - porcAsentamiento / 100);
        XYSeries reto = new XYSeries("Banda inferior");
        Number numberTau = maxTime;
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
        for (XYSeries key : seriesSinColor) {
            int seriesIndex = data.indexOf(key);
            Paint paint = renderer.getSeriesPaint(seriesIndex);
            if (paint == null) {
                paint = ((AbstractRenderer) renderer).lookupSeriesPaint(seriesIndex);
            }
            colores.add((Color) paint);
        }
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

    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel(new String[]{"Categoria", "Overshoot", "Tiempo Caida", "Tiempo Asentamiento"}, 0);
        //DataInput di = input_catalog.get(0);
        for (DataInput di : input_catalog) {
            for (Double psi : psiList) {
                List<String> row = new ArrayList<String>();
                row.add(di.getLabel() + " -- " + psi);//categoria
                if (psi < 1) {
                    Double overshoot = getOvershoot(psi);
                    row.add(Utilidades.DECIMAL_FORMATTER.format(overshoot));
                    row.add(Utilidades.DECIMAL_FORMATTER.format(getDecayRatio(overshoot)));
                } else {
                    row.add("-");
                    row.add("-");
                }
                row.add(Utilidades.DECIMAL_FORMATTER.format(getTiempoAsentamiento(porcAsentamiento, psi, di)));
                tmodel.addRow(row.toArray(new String[0]));
            }
        }
        return tmodel;
    }
    //en vez de calcularlo se saca de grafica

    private Double getTiempoAsentamiento(Double porc, Double psi, DataInput di) {
        double result = 0D;
        boolean contenido = false;
        double valSup = di.getAmplitud() * (1 + porc / 100);
        double valInf = di.getAmplitud() * (1 - porc / 100);
        if (psi < 1) {
            for (double time = 0; time <  NCTE_TAU_GRAFICA * maxTau ; time = time + DataInput.JUMP) {
                //valor de Y(t)
                double value = di.getAmplitud() * (1 - ((1 / (Math.sqrt(1 - Math.pow(psi, 2)))) * (Math.exp((-psi * time) / di.getTau())) * (Math.sin(
                    ((Math.sqrt(1 - Math.pow(psi, 2))) * (time / di.getTau())) + Math.atan((Math.sqrt(1 - Math.pow(psi, 2))) / psi)))));
                if (value > valInf && value < valSup) {
                    if(!contenido){
                        result = time;
                        contenido=true;
                    }
                }else{
                    contenido=false;
                }

            }
        } else {
            
            for (double time = 0; time < NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
                //valor de Y(t)
                double value = getfdet(di, time, psi);
                if (value > valInf && value < valSup) {
                    if(!contenido){
                        result = time;
                        contenido=true;
                    }
                }else{
                    contenido=false;
                }

            }
        }
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
        return new MyColorCellRenderer(colores);
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void main(String args[]){
        FuncionTransferencia.DEBUG = true;
        pnlEntradaEscalon.main(args);
    }
}