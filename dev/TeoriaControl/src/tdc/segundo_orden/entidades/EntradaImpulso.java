/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.segundo_orden.entidades;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import tdc.Utilidades;
import tdc.entidades.DataInput;
import tdc.entidades.FuncionTransferencia;
import tdc.gui.entidades.MyColorCellRenderer;
import tdc.segundo_orden.gui.EntradaEscalonOrdenDosForm;
import tdc.util.ApplicationConstants;

/**
 * 5Tau en cte tiempo
 * @author fanky
 */
public class EntradaImpulso extends FuncionTransferencia {
    public static String CHART_TITLE = "Respuesta Transiente Sistema Segundo orden: Entrada tipo Impulso";
    private Double maxTau = 0D;
    private Boolean dibujarAmplitud = true;
    private Double psi;

    public EntradaImpulso(EntradaEscalonOrdenDosForm input, Boolean dibujarAmplitud) {
        super(input);
        this.dibujarAmplitud = dibujarAmplitud;
        this.psi = input.getPsi();
        init();
    }

    public EntradaImpulso(EntradaEscalonOrdenDosForm input) {
        this(input, true);
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

    @Override
    protected double getfdet(DataInput di, double time) {
        
        if (psi < 1) {
            Double t1First = 1 / (Math.sqrt(1 - Math.pow(psi, 2) * di.getTau()));
            debug("t1First: " + t1First);
            Double t1Second = Math.pow(Math.E, ((-psi * time )/ di.getTau()));
            debug("t1Second: " + t1Second);
            Double secondTerm1 = t1First * t1Second;
            debug("secondTerm1: " + secondTerm1);
            Double sinFirst = Math.sin(Math.sqrt(1 - Math.pow(psi, 2)) * time / di.getTau());
            debug("sinFirst: " + sinFirst);
            
            return t1First * t1Second * sinFirst;
        
        } else if( psi == 1 ) {
            
            return 1;
            
        } else {
            return 1; 
        }

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

    private XYSeries getMainChart(DataInput di) {
         XYSeries reto = new XYSeries(di.getLabel());
        debug("generating Graphic tau: " + di.getTau() + " amplitud: " + di.getAmplitud());
        debug("psi: " + psi);
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
    
}
