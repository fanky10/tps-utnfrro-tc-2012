/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import tdc.entidades.DataInput;
import tdc.entidades.FuncionTransferencia;
import tdc.segundo_orden.gui.EntradaSenoidalOrdenDosForm;
import tdc.segundo_orden.gui.pnlEntradaSenoidal;

/**
 *
 *
 * entrada - salida 50 - 5 - 5 - 0.5
 * @author fanky
 */
public class EntradaSenoidal extends FuncionTransferencia {

    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Senoidal";
    private double maxTime = 0D;
    private double psi = 0D;
    private double maxValorAmplitud = 0D;
    private double minValorAmplitud = Double.MAX_VALUE;

    public EntradaSenoidal(EntradaSenoidalOrdenDosForm input) {
        super(input);
        psi = input.getPsi();
        init();
    }

    private void init() {

        for (DataInput di : input_catalog) {
            if (maxTime < di.getPeriodo() * DataInput.NCTE_TAU_GRAFICA) {
                maxTime = di.getPeriodo() * DataInput.NCTE_TAU_GRAFICA;
            }
        }
    }

    @Override
    public void createDataset() {
        //tendria que iterar solo una vez 
        for (DataInput di : input_catalog) {
            showVars(di);
            data.addSeries(getEntrada(di));
            data.addSeries(getRespuesta(di));
            data.addSeries(getValorBase(di));
        }

    }

    private XYSeries getEntrada(DataInput di) {
        XYSeries reto = new XYSeries("Entrada");
        DataInput.JUMP = 0.01D;
        //double y = (a*(Math.Sin(w*i))) + vb;
        debug("Entrada: X(t) = A * sin(wt) + vBase");
        debug("X(t) = " + di.getAmplitud() + "*sin(" + di.getOmega() + "*t) +" + di.getValor_base());
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            //s/me
            double value = di.getAmplitud() * Math.sin(di.getOmega() * time) + di.getValor_base();
            setMaxValorAmplitud(value);
            setMinValorAmplitud(value);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getRespuesta(DataInput di) {
        XYSeries reto = new XYSeries("Respuesta Total");
        DataInput.JUMP = 0.01D;
        //opc 2
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            double value = getfdet(di, time);
            setMaxValorAmplitud(value);
            setMinValorAmplitud(value);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getValorBase(DataInput di) {
        XYSeries reto = new XYSeries("Valor Base");
        reto.add(0, di.getValor_base());
        reto.add(maxTime, di.getValor_base());

        return reto;
    }

    @Override
    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
                CHART_TITLE, // chart title
                "t", // domain axis label
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
        conf_range_plot(plot);
        conf_domain_plot(plot);
    }

    private void conf_range_plot(XYPlot plot) {

        double maxBaseValue = 0;
        for (DataInput di : input_catalog) {
            if (maxBaseValue < di.getValor_base()) {
                maxBaseValue = di.getValor_base();
            }
        }
        double porc = 0.01;
        double bandaSuperior = getMaxValorAmplitud() * (1 + porc);
        double bandaInferior = getMinValorAmplitud() * (1 - porc);
        
        debug("bandaSup: " + bandaSuperior);
        debug("bandaInf: " + bandaInferior);

        final NumberAxis functionAxis = new NumberAxis("y(t)");
        functionAxis.setInverted(false);
        //2veces la diferencia entre max_base y el mas alto de los peaks
        functionAxis.setRange(bandaInferior, bandaSuperior);
        plot.setRangeAxis(functionAxis);

    }

    private void conf_domain_plot(XYPlot plot) {
        //las x
        ValueAxis currentDomainAxis = plot.getDomainAxis();
        currentDomainAxis.setRange(0, maxTime);//0,4D * max_period_val + 3D * max_tau_val);

    }

    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel();
        tmodel.setColumnIdentifiers(new Object[]{"Cta de Tiempo", "Ret Fase Grados", "Ret Fase Min", "Ret Fase Rad"});
        double tau = 0.01D;
        for (DataInput di : input_catalog) {
            for (double i = 0.01; i <= 1000; i = i * 10) {
                debug("tau: " + tau + "iterator: " + i);
                tmodel.addRow(new Object[]{i,
                            (double) Math.round(di.getPhaseLag(i) * 100) / 100,
                            convertToMin((double) Math.round(di.getPhaseLag(i) * 100) / 100),
                            convertToRad((double) Math.round(di.getPhaseLag(i) * 100) / 100)});
            }
        }
        tmodel.addRow(new Object[]{"∞", "-90"});
        return tmodel;
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        double sqrtOne = Math.pow((1 - Math.pow(di.getOmega() * di.getTau(), 2)), 2);
        double sqrtTwo = Math.pow(2 * psi * di.getOmega() * di.getTau(), 2);
        double sin = Math.sin(di.getOmega() * time + getPhaseLag(di));
        double sqrt = Math.sqrt(sqrtOne + sqrtTwo);
        double result = 1 / sqrt * sin;
        debug("sin: " + sin);
        debug("sqrt: " + sqrt);
        debug("result: " + result);
        return di.getAmplitud() * result + di.getValor_base();
    }

    private double getPhaseLag(DataInput di) {
        double nomin = 2 * psi * di.getOmega() * di.getTau();
        double denomin = 1 - Math.pow(di.getOmega() * di.getTau(), 2);
        return -Math.atan(nomin / denomin);
    }

    private void showVars(DataInput di) {
        debug("CteTiempo: " + di.getTau());
        debug("Amplitud: " + di.getAmplitud());
        debug("Valor Base: " + di.getValor_base());
//        debug("Valor Maximo: " + di.getValor_max());
        debug("Frecuencia: " + di.getFrecuencia());
        debug("Omega: " + di.getOmega());
        debug("Period: " + di.getPeriodo());
        debug("Phase lag: " + di.getPhaseLag());
    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new DefaultTableCellRenderer();
    }

    private Object convertToMin(double d) {
        double decNum = (d * Math.PI) / 3600;
        decNum = (double) Math.round(decNum * 100) / 100;
        return decNum;
    }

    private Object convertToRad(double d) {
        double rad = d * (Math.PI / 180);
        rad = (double) Math.round(rad * 100) / 100;
        return rad;
    }

    public void setMaxValorAmplitud(double maxValorAmplitud) {
        if (this.maxValorAmplitud < maxValorAmplitud) {
            this.maxValorAmplitud = maxValorAmplitud;
        }
    }

    public double getMaxValorAmplitud() {
        return maxValorAmplitud;
    }

    public double getMinValorAmplitud() {
        return minValorAmplitud;
    }

    public void setMinValorAmplitud(double minValorAmplitud) {
        if (this.minValorAmplitud > minValorAmplitud) {
            this.minValorAmplitud = minValorAmplitud;
        }
    }

    //otra forma mas elaborada...
    /** 
     * JFreeChart chart = ... create chart here ...
     * List<Color> colorList = .. create list with colors here ...
     * CategoryPlot cp = chart.getCategoryPlot();
     * DrawingSupplier ds = new AmisDrawingSupplier(colorList);
     * cp.setDrawingSupplier(ds);
     */
    class AmisDrawingSupplier implements DrawingSupplier {

        private Stroke stroke = new BasicStroke();
        private Shape shape = new Rectangle2D.Double();
        private int cursor = 0;
        private List<Color> colorList;

        public AmisDrawingSupplier(List<Color> colorList) {
            this.colorList = colorList;
        }

        public Paint getNextPaint() {
            if (colorList == null || colorList.isEmpty()) {
                return Color.RED; //return red on empty or no list
            }

            Color returnColor = colorList.get(cursor);

            cursor++;

            //wrap cursor when all items in the list are traversed        if (cursor >= colorList.size()) {            cursor = 0;        }

            return returnColor;
        }

        public Paint getNextOutlinePaint() {
            return Color.BLACK;
        }

        public Stroke getNextStroke() {
            return stroke;
        }

        public Stroke getNextOutlineStroke() {
            return stroke;
        }

        public Shape getNextShape() {
            return shape;
        }

        public Paint getNextFillPaint() {
            return getNextPaint();
        }
    }

    public static void main(String args[]) {
        FuncionTransferencia.DEBUG = true;
        pnlEntradaSenoidal.main(args);
    }
}
