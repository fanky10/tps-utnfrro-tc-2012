/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;

import java.util.ArrayList;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author fanky
 */
public class XYSeriesCollection extends ArrayList<XYSeries>{
    private ValueAxis domainAxis;
    private ValueAxis rangeAxis;

    public ValueAxis getDomainAxis() {
        return domainAxis;
    }

    public void setDomainAxis(ValueAxis domainAxis) {
        this.domainAxis = domainAxis;
    }

    public ValueAxis getRangeAxis() {
        return rangeAxis;
    }

    public void setRangeAxis(ValueAxis rangeAxis) {
        this.rangeAxis = rangeAxis;
    }
    

}
