/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
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
import tdc.segundo_orden.gui.pnlEntradaImpulso;
import tdc.util.CurvaUtil;

/**
 * 5Tau en cte tiempo
 *
 * @author fanky
 */
public class EntradaImpulso extends FuncionTransferencia {

    private static final double NCTE_TAU_GRAFICA = 20D;
    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Impulso";
    private Double maxTau = 0D;
    private java.util.List<Double> psiList = new ArrayList<Double>();
    private Double porcAsentamiento = 0D;
    private List<XYSeries> seriesSinColor = new ArrayList<XYSeries>();
    private Map<XYSeries, Linea> lineasMap = new LinkedHashMap<XYSeries, Linea>();
    private Double tiempoAsentamiento = -1D;//not set
    private CurvaUtil curvaUtil;
    private Double maxTime = 0D;

    public EntradaImpulso(EntradaEscalonImpulsoOrdenDosForm input) {
        super(input);
        this.psiList = input.getPsi();
        this.porcAsentamiento = input.getPorcentajeAsentamiento();
        init();
    }

    private void init() {
        curvaUtil = new CurvaUtil() {

            @Override
            public double getfdet(DataInput di, double time, Double psi) {
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
                return di.getAmplitud() * result;
            }

            @Override
            public double getBandaSuperior(DataInput di, Double porcAsentamiento) {
                return di.getAmplitud() * (porcAsentamiento / 100);

            }

            @Override
            public double getBandaInferior(DataInput di, Double porcAsentamiento) {
                return -di.getAmplitud() * (porcAsentamiento / 100);
            }
        };

        for (DataInput di : input_catalog) {
            if (maxTau < di.getTau()) {
                maxTau = di.getTau();
            }
        }
        for (Double psi : psiList) {
            for (DataInput di : input_catalog) {
                double ultimoPico = curvaUtil.getPrimerPicoBetween(di, psi, NCTE_TAU_GRAFICA * maxTau, porcAsentamiento);
                maxTime = (ultimoPico > maxTime ? ultimoPico : maxTime);//lo sobreescribo si es mayor
            }
        }
        debug("maxTime: " + maxTime);
        maxTime = maxTime * 1.5;//un 50% mas.
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
        return di.getAmplitud() * result;

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

    private XYSeries getAmplitud(DataInput di) {
        XYSeries reto = new XYSeries("Amplitud");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, 0);
        reto.add(numberTau, 0);
        return reto;
    }

    private XYSeries getBandaSuperior(DataInput di) {
        Double banda = di.getAmplitud() * (porcAsentamiento / 100);
        XYSeries reto = new XYSeries("Banda superior");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, banda);
        reto.add(numberTau, banda);
        return reto;
    }

    private XYSeries getBandaInferior(DataInput di) {
        Double banda = -di.getAmplitud() * (porcAsentamiento / 100);
        XYSeries reto = new XYSeries("Banda inferior");
        Number numberTau = NCTE_TAU_GRAFICA * maxTau;
        reto.add(0, banda);
        reto.add(numberTau, banda);
        return reto;
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

//    private Double getTiempoAsentamiento(Double porc, Double psi, Double tau) {
//        return -Math.log(porc / 100) / (psi * tau);
//    }
    private Double getOvershoot(Double psi) {
        Double exp = -(Math.PI * psi) / (Math.sqrt(1 - Math.pow(psi, 2)));
        return Math.pow(Math.E, exp);

    }

    private Double getDecayRatio(Double overshoot) {
        return Math.pow(overshoot, 2);
    }

    private Double getTiempoAsentamiento(Double porc, Double psi, DataInput di) {
        double result = 0D;
        boolean contenido = false;
        double valSup = di.getAmplitud() * (porc / 100);
        double valInf = -di.getAmplitud() * (porc / 100);
        if (psi < 1) {
//            double maxTime = curvaUtil.getPrimerPicoBetween(di, psi, NCTE_TAU_GRAFICA * maxTau, porcAsentamiento);
            for (double time = 0; time < NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
                //valor de Y(t)
                double value = di.getAmplitud() * ((1 / (Math.sqrt(1 - Math.pow(psi, 2))) * di.getTau()) * Math.exp((-psi * time) / di.getTau()) * Math.sin((Math.sqrt(1 - Math.pow(psi, 2))) * (time / di.getTau())));

                if (value > valInf && value < valSup) {
                    if (!contenido) {
                        result = time;
                        contenido = true;
                    }
                } else {
                    contenido = false;
                }

            }
        } else {

            for (double time = 0; time < NCTE_TAU_GRAFICA * maxTau; time = time + DataInput.JUMP) {
                //valor de Y(t)
                double value = getfdet(di, time, psi);
                if (value > valInf && value < valSup) {
                    if (!contenido) {
                        result = time;
                        contenido = true;
                    }
                } else {
                    contenido = false;
                }

            }
        }
        return result;
    }

    public static void main(String arg[]) {
        FuncionTransferencia.DEBUG = true;
        pnlEntradaImpulso.main(arg);
    }
}
