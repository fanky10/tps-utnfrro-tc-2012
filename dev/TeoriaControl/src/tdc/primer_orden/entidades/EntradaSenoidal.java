/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.primer_orden.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Comparator;
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
import tdc.entidades.DataInputCatalog;
import tdc.entidades.FuncionTransferencia;

/**
 *
 *
 * entrada - salida 50 - 5 - 5 - 0.5
 * @author fanky
 */
public class EntradaSenoidal extends FuncionTransferencia {

    public EntradaSenoidal(DataInputCatalog input) {
        super(input);
    }

    @Override
    public void createDataset() {
        //tendria que iterar solo una vez 
        for (DataInput di : input_catalog) {
            showVars(di);
            data.addSeries(getEntrada(di));
            data.addSeries(getRespuestaSS(di));
            data.addSeries(getRespuestaTotal(di));
            data.addSeries(getValorBase(di));
        }

    }

    private XYSeries getEntrada(DataInput di) {
        XYSeries reto = new XYSeries("Entrada");
        double max_time = 4 * di.getPeriodo() + 4 * di.getTau();//4 periodos 4tau para que se tranquilice :P
        DataInput.JUMP = 0.01D;
        //double y = (a*(Math.Sin(w*i))) + vb;
        debug("Entrada: X(t) = A * sin(wt) + vBase");
        debug("X(t) = " + di.getAmplitud() + "*sin(" + di.getOmega() + "*t) +" + di.getValor_base());
        for (double time = 0; time < max_time; time = time + DataInput.JUMP) {
            //s/me
            double value = di.getAmplitud() * Math.sin(di.getOmega() * time) + di.getValor_base();
            reto.add(time, value);
        }
        return reto;
    }
    
    private XYSeries getRespuestaTotal(DataInput di){
        XYSeries reto = new XYSeries("Respuesta Total");
        double max_time = 4 * di.getPeriodo() + 4 * di.getTau();//4 periodos 4tau para que se tranquilice :P
        DataInput.JUMP = 0.01D;
        //opc 2
        for (double time = 0; time < max_time; time = time + DataInput.JUMP) {
            double value = getfdet(di, time, true);
            reto.add(time, value);
        }
        return reto;
    }
    private XYSeries getRespuestaSS(DataInput di){
        XYSeries reto = new XYSeries("Respuesta SS");
        
        double max_time = 4 * di.getPeriodo() + 4 * di.getTau();//4 periodos 4tau para que se tranquilice :P
        DataInput.JUMP = 0.01D;
        //opc 2
        for (double time = 0; time < max_time; time = time + DataInput.JUMP) {
            double value = getfdet(di, time, false);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getValorBase(DataInput di) {
        XYSeries reto = new XYSeries("Valor Base");
        double max_time = 4 * di.getPeriodo() + 4 * di.getTau();
        reto.add(0, di.getValor_base());
        reto.add(max_time, di.getValor_base());

        return reto;
    }

    @Override
    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
                "Entrada Senoidal", // chart title
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
        double maxAmplitud = 0;
        for (DataInput di : input_catalog) {
            if(maxBaseValue < di.getValor_base()){
                maxBaseValue = di.getValor_base();
            }
            if(maxAmplitud < di.getAmplitud()){
                maxAmplitud = di.getAmplitud();//+2 (?)
            }
        }
        final NumberAxis functionAxis = new NumberAxis("y(t)");
        functionAxis.setInverted(false);
        //2veces la diferencia entre max_base y el mas alto de los peaks
        functionAxis.setRange(maxBaseValue - maxAmplitud, maxBaseValue + maxAmplitud);
        plot.setRangeAxis(functionAxis);
        
    }

    private void conf_domain_plot(XYPlot plot) {
        //las x
        ValueAxis currentDomainAxis = plot.getDomainAxis();
        Collections.sort(input_catalog, new Comparator<DataInput>() {

            public int compare(DataInput o1, DataInput o2) {
                if (o1.getTau() == o2.getTau()) {
                    return 0;
                }
                return (o1.getTau() > o2.getTau()) ? 1 : -1;
            }
        });
        double max_tau_val = input_catalog.get(0).getTau();
        Collections.sort(input_catalog, new Comparator<DataInput>() {

            public int compare(DataInput o1, DataInput o2) {
                if (o1.getPeriodo() == o2.getPeriodo()) {
                    return 0;
                }
                return (o1.getPeriodo() > o2.getPeriodo()) ? 1 : -1;
            }
        });
        double max_period_val = input_catalog.get(0).getPeriodo();
        double max_range = 4 * max_period_val + 3 * max_tau_val;
        debug("DEBUG: Domain axis " + max_range);
        currentDomainAxis.setRange(0, max_range);//0,4D * max_period_val + 3D * max_tau_val);

    }


    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel();
        tmodel.setColumnIdentifiers(new Object[]{"Tau", "Phi"});
        double tau = 0.01D;
        for (DataInput di : input_catalog) {
            for (double i = 0.01; i < 1000; i = i * 10) {
                debug("tau: " + tau + "iterator: " + i);
                tmodel.addRow(new Object[]{i, di.getPhaseLag(i)});
            }
        }
        return tmodel;
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        return getfdet(di, time, true);
    }
    protected double getfdet(DataInput di, double time,boolean rtaTotal) {
        double result = 0;
        if(rtaTotal){
            result = di.getValor_base() + getFirstTerm(di, time) + getSecondTerm(di, time);
        }else{
            result = di.getValor_base() + getSecondTerm(di, time);
        }
        return result;
        
    }

    private double getFirstTerm(DataInput di, double time) {
        return ((di.getAmplitud() * di.getOmega() * di.getTau()) / (Math.pow(di.getTau(), 2) * Math.pow(di.getOmega(), 2) + 1)) * Math.pow(Math.E, (-time / di.getTau()));
    }

    private double getSecondTerm(DataInput di, double time) {
        return (di.getAmplitud() / Math.sqrt(Math.pow(di.getTau(), 2) * Math.pow(di.getOmega(), 2) + 1)) * Math.sin(di.getOmega() * time + di.getPhaseLag());
    }

    private void showVars(DataInput di) {
        debug("CteTiempo: " + di.getTau());
        debug("Amplitud: " + di.getAmplitud());
        debug("Valor Base: " + di.getValor_base());
        debug("Frecuencia: " + di.getFrecuencia());
        debug("Omega: " + di.getOmega());
        debug("Period: " + di.getPeriodo());
        debug("Phase lag: " + di.getPhaseLag());
    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new DefaultTableCellRenderer();
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
}
