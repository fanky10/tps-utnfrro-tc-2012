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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * @author Fanky10 <fanky10@gmail.com>
 */
public class EntradaSenoidalDOS extends FuncionTransferencia {
    
    public static String CHART_TITLE = "Respuesta Transiente Sistema Primer orden: Entrada tipo Senoidal";
    private double maxTime = 0D;

    public EntradaSenoidalDOS(DataInputCatalog input) {
        super(input);
        init();
    }

    private void init() {

        for (DataInput di : input_catalog) {
            if (maxTime < di.getPeriodo() * 4) {
                maxTime = di.getPeriodo() * 4;
            }
        }
    }

//    @Override
    public void createDataset() {
        //tendria que iterar solo una vez 
        for (DataInput di : input_catalog) {
            showVars(di);
            data.addSeries(getEntrada(di));
            data.addSeries(getRespuestaSS(di));
            data.addSeries(getRespuestaTotal(di));
            data.addSeries(getValorBase(di));
            data.addSeries(getPeriodoEntrada(di));
            data.addSeries(getLag(di));
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
            double value = getFEntrada(di, time);
            reto.add(time, value);
        }
        return reto;
    }

    private double getFEntrada(DataInput di, Double time) {
        return di.getAmplitud() * Math.sin(di.getOmega() * time) + di.getValor_base();
    }

    private XYSeries getRespuestaTotal(DataInput di) {
        XYSeries reto = new XYSeries("Respuesta Total");
        DataInput.JUMP = 0.01D;
        //opc 2
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            double value = getfdet(di, time, true);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getRespuestaSS(DataInput di) {
        XYSeries reto = new XYSeries("Respuesta SS");
        DataInput.JUMP = 0.01D;
        //opc 2
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            double value = getfdet(di, time, false);
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

//    @Override
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
        double maxAmplitud = 0;
        for (DataInput di : input_catalog) {
            if (maxBaseValue < di.getValor_base()) {
                maxBaseValue = di.getValor_base();
            }
            if (maxAmplitud < di.getAmplitud()) {
                maxAmplitud = di.getAmplitud();//+2 (?)
            }
        }
        double changui = maxAmplitud * 0.05;
        final NumberAxis functionAxis = new NumberAxis("y(t)");
        functionAxis.setInverted(false);
        //2veces la diferencia entre max_base y el mas alto de los peaks
        functionAxis.setRange(maxBaseValue - maxAmplitud - changui, maxBaseValue + maxAmplitud + changui);
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
        return getfdet(di, time, true);
    }

    protected double getfdet(DataInput di, double time, boolean rtaTotal) {
        double result = 0;
        if (rtaTotal) {
            result = di.getValor_base() + getFirstTerm(di, time) + getSecondTerm(di, time);
        } else {
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

    private Object convertToMin(double d) {
        int intNum = (int) d;
        double decNum = d - intNum;
        String minAndSec = getMinAndSec(decNum);

        String min = intNum + "°" + minAndSec;

        return min;
    }

    private Object convertToRad(double d) {
        double rad = d * (Math.PI / 180);
        rad = (double) Math.round(rad * 100) / 100;
        return rad;
    }

    private String getMinAndSec(double decNum) {
        String minAndSec = "";
        double minutes = decNum * 60;
        int min = (int) minutes;
        double seconds = minutes - min;
        seconds = (double) Math.round((seconds * 60) * 100) / 100;;
        minAndSec = min + "' " + seconds + "''";
        return minAndSec;
    }

    private XYSeries getPeriodoEntrada(DataInput di) {
        XYSeries reto = new XYSeries("1 Periodo Entrada");
        Map<Double, Double> values = new LinkedHashMap<Double, Double>();
        double beforeValue = 0;
        boolean estaBajando = false;
        int puntosFuncion = 0;
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            //s/me
            double currentValue = getFEntrada(di, time);
            boolean esPico = esPico(currentValue, beforeValue, di.getValor_base());
            if (esPico && !estaBajando) {
                values.put(time, currentValue);
                estaBajando = true;
                puntosFuncion++;
            } else if (!esPico) {
                estaBajando = false;
            }

            if (puntosFuncion == 2) {
                //solo queremos una linea
                break;
            }
            beforeValue = currentValue;//sobreescribimos el valor

        }
        for (Double key : values.keySet()) {
            Double val = values.get(key);
            reto.add(key, val);
        }

        return reto;
    }

    private boolean esPico(Double currentValue, Double beforeValue, Double trasehold) {
        return currentValue < beforeValue && currentValue > trasehold;
    }

    private XYSeries getLag(DataInput di) {
        XYSeries reto = new XYSeries("Lag");
        Double[] segundoPicoEntrada = getPico(2, di, 0);
        Double[] segundoPicoSS = getPico(2, di, 1);
        reto.add(segundoPicoEntrada[0], segundoPicoEntrada[1]);
        reto.add(segundoPicoSS[0], segundoPicoEntrada[1]);
        reto.add(segundoPicoSS[0], segundoPicoSS[1]);
        

        return reto;
    }

    private Double[] getPico(int nro, DataInput di, int tipoFuncion) {
//        Map<Double, Double> values = new LinkedHashMap<Double, Double>();
        Double[] values = new Double[2];
        double beforeValue = 0;
        boolean estaBajando = false;
        int nroPico = 0;
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            //s/me
            double currentValue = 0;
            if (tipoFuncion == 0) {
                currentValue = getFEntrada(di, time);
            }else if (tipoFuncion == 1){
                currentValue = getfdet(di, time,false);
            }
            boolean esPico = esPico(currentValue, beforeValue, di.getValor_base());
            if (esPico && !estaBajando) {
                estaBajando = true;
                nroPico++;
            } else if (!esPico) {
                estaBajando = false;
            }

            if (nroPico == nro) {
                //solo queremos una linea
//                values.put(time, currentValue);
                values[0] = time;
                values[1] = currentValue;
                break;
            }
            beforeValue = currentValue;//sobreescribimos el valor
        }
        return values;
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
