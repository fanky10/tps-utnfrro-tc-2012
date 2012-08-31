/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.primer_orden.entidades;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import tdc.Utilidades;
import tdc.entidades.DataInput;
import tdc.entidades.DataInputCatalog;
import tdc.entidades.FuncionTransferencia;
import tdc.gui.entidades.MyColorCellRenderer;

/**
 * 5Tau en cte tiempo
 * @author fanky
 */
public class EntradaImpulso extends FuncionTransferencia {

    private Double maxTau = 0D;

    public EntradaImpulso(DataInputCatalog input) {
        super(input);
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
    public void createDataset() {
        for (DataInput di : input_catalog) {
            data.addSeries(getMainChart(di));
            data.addSeries(getCteTiempo(di));
        }
    }

    @Override
    public DefaultTableModel createTableModel() {
        DefaultTableModel tmodel = new DefaultTableModel(new Object[]{"Categoria", "Tiempo Subida Grafica", "Tiempo Subida Algebraico (2.2*Tau)"}, 0);
        for (DataInput di : input_catalog) {
            tmodel.addRow(new Object[]{di.getLabel(),
                        Utilidades.DECIMAL_FORMATTER.format(getTiempoSubida(di)),
                        Utilidades.DECIMAL_FORMATTER.format(getPorcentajeAlgebraico(di))
                    });
        }
        return tmodel;
    }

    public double getPorcentajeTabla(DataInput di) {
        double one_tau = getfdet(di, di.getTau());
        return one_tau / di.getAmplitud();
    }

    private XYSeries getMainChart(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel());
        debug("generating Graphic di.getTau(): " + di.getTau() + " di.getAmplitud(): " + di.getAmplitud());
        for (double time = 0; time < DataInput.N_TAU * maxTau; time = time + DataInput.JUMP) {
            //valor de Y(t)
            double value = getfdet(di, time);
            debug("Y(" + time + ") generado: " + value);
            reto.add(time, value);
        }
        return reto;
    }

    private XYSeries getCteTiempo(DataInput di) {
        XYSeries reto = new XYSeries(di.getLabel() + "1di.getTau()");
        //el valor de y(t) cuando t=1di.getTau()
        double value = getfdet(di, di.getTau());
//        debug("============================================");
//        debug("generating cte tiempo "+value);
        reto.add(0, value);
        reto.add(di.getTau(), value);
        reto.add(di.getTau(), 0);
        return reto;
    }

    public double getPorcentajeAlgebraico(DataInput di) {
        return 2.2D * di.getTau();
    }

    public double getTiempoSubida(DataInput di) {
        debug("---------------TIEMPO DE SUBIDA--------------");
        double tmin = 0d;//al 10% de la di.getAmplitud()
        double tmax = 0d;//al 90% de la di.getAmplitud()
        //buscamos el tmin
        debug("--tmin seek");
        double ytmin = di.getAmplitud() * 0.1;
        double ytmax = di.getAmplitud() * 0.9;
        tmin = di.getTau() * Math.log(di.getTau() * ytmin);
        debug("tmin found! " + tmin);
        tmax = di.getTau() * Math.log(di.getTau() * ytmax);
        debug("tmax found! " + tmax);
        return tmax - tmin;
    }

    @Override
    protected double getfdet(DataInput di, double time) {
        return (di.getAmplitud() / di.getTau()) * Math.pow(Math.E, (-time / di.getTau()));
    }

    @Override
    protected void createChart() {
        chart = ChartFactory.createXYLineChart(
                "Entrada Impulso", // chart title
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
        plot.setDomainAxis(timeAxis);



    }

    @Override
    public TableCellRenderer createTableRenderer() {
        return new MyColorCellRenderer(input_catalog);
    }
}
