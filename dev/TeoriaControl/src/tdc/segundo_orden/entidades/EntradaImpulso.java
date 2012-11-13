/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.Utilidades;
import tdc.entidades.DataInput;
import tdc.entidades.FuncionTransferencia;
import tdc.entidades.Linea;
import tdc.gui.entidades.MyColorCellRenderer;
import tdc.segundo_orden.gui.EntradaEscalonImpulsoOrdenDosForm;

/**
 * 5Tau en cte tiempo
 * @author fanky
 */
public class EntradaImpulso extends FuncionTransferencia {

    private static final double NCTE_TAU_GRAFICA = 10D;
    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Impulso";
    private Double maxTau = 0D;
    private java.util.List<Double> psiList = new ArrayList<Double>();
    private Double porcAsentamiento = 0D;
    private List<XYSeries> seriesSinColor = new ArrayList<XYSeries>();

    public EntradaImpulso(EntradaEscalonImpulsoOrdenDosForm input) {
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
    }

    @Override
    protected void createDataset() {
        data = new XYSeriesCollection();
        colores = new ArrayList<Color>();
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                XYSeries xys = getMainChart(di, psi);
                data.addSeries(xys);
                seriesSinColor.add(xys);
            }
        }
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        return 0D;

    }

    protected double getfdet(DataInput di, double time, double psi) {
        Double result = 0D;
        if (psi < 1) {
            Double t1First = 1 / (Math.sqrt(1 - Math.pow(psi, 2)) * di.getTau());
            debug("t1First: " + t1First);
            Double t1Second = Math.pow(Math.E, ((-psi * time) / di.getTau()));
            debug("t1Second: " + t1Second);
            Double secondTerm1 = t1First * t1Second;
            debug("secondTerm1: " + secondTerm1);
            Double sinFirst = Math.sin(Math.sqrt(1 - Math.pow(psi, 2)) * time / di.getTau());
            debug("sinFirst: " + sinFirst);

            result = t1First * t1Second * sinFirst;

        } else if (psi == 1) {
            Double t1First = (1 / Math.pow(di.getTau(), 2)) * time;
            debug("t1First: " + t1First);
            Double t1Second = Math.pow(Math.E, (-time / di.getTau()));
            debug("t1Sdecond: " + t1Second);

            result = t1First * t1Second;

        } else {
            Double t1First = time / Math.pow(di.getTau(), 2);
            debug("t1First: " + t1First);
            Double t1Second = 1 / Math.sqrt(Math.pow(psi, 2) - 1);
            debug("t1Second: " + t1Second);
            Double t1Third = Math.pow(Math.E, (-psi * time / di.getTau()));
            debug("t1Third: " + t1Third);
            Double sinFirst = Math.sinh(Math.sqrt(Math.pow(psi, 2) - 1));
            debug("sinFirst: " + sinFirst);

            result = t1First * t1Second * t1Third * sinFirst;

        }
        return result;

    }

    public double getPorcentajeAlgebraico(DataInput di) {
        return 2.2D * di.getTau();
    }

    @Override
    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
                CHART_TITLE,
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
        timeAxis.setRange(0.0, NCTE_TAU_GRAFICA * maxTau);
        plot.setDomainAxis(timeAxis);



    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new MyColorCellRenderer(colores);
    }

    private XYSeries getMainChart(DataInput di) {
        throw new UnsupportedOperationException("not supported AT ALL LOL");
    }

    private XYSeries getMainChart(DataInput di, Double psi) {
        XYSeries reto = new XYSeries(di.getLabel() + " -- " + psi);
        debug("generating Graphic tau: " + di.getTau() + " amplitud: " + di.getAmplitud());
        debug("psi: " + psi);
        for (double time = 0; time < NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
            //valor de Y(t)
            double value = getfdet(di, time, psi);
            debug("Y(" + time + ") generado: " + value);
            reto.add(time, value);
        }
        return reto;
    }

    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel(new String[]{"Categoria", "Overshoot", "Tiempo Subida", "Tiempo Caida"}, 0);
        //DataInput di = input_catalog.get(0);
        for(DataInput di: input_catalog){
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
                row.add(Utilidades.DECIMAL_FORMATTER.format(getTiempoAsentamiento(porcAsentamiento, psi, di.getTau())));
                tmodel.addRow(row.toArray(new String[0]));
            }
        }              
        return tmodel;
    }

    private Double getTiempoAsentamiento(Double porc, Double psi, Double tau) {
        return -Math.log(porc / 100) / (psi * tau);
    }

    private Double getOvershoot(Double psi) {
        Double exp = -(Math.PI * psi) / (Math.sqrt(1 - Math.pow(psi, 2)));
        return Math.pow(Math.E, exp);

    }

    private Double getDecayRatio(Double overshoot) {
        return Math.pow(overshoot, 2);
    }

//    private Double getTiempoAsentamiento(DataInput di) {
//        return DataInput.NCTE_TAU_TABLA * di.getTau();
//    }
}
