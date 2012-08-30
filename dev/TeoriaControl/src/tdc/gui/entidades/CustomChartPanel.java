/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.gui.entidades;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author facundo
 */
public class CustomChartPanel extends JPanel implements ChartModelListener{
    private ChartModel model;
//    private LayoutManager manager;
    public CustomChartPanel(ChartModel model,LayoutManager manager ){
        super(manager);
        setModel(model);
    }
    public CustomChartPanel(ChartModel model){
        this(model,new BorderLayout());
    }
    public CustomChartPanel(){
        this(new DefaultChartModel(),new BorderLayout());
    }
    
    public void panelChanged(ChartModelEvent evt) {
        this.updateUI();
    }
    
    public void setModel(ChartModel model){
        if(model==null){
            System.out.println("you cannot set me an empty model!");
            return;
        }
        if (this.model != model) {
            ChartModel old = this.model;
            if(old!=null){
                this.remove(old.getPanelActual());
            }
            this.model = model;
            this.add(this.model.getPanelActual(),BorderLayout.CENTER);
            
            panelChanged(new ChartModelEvent(model));
            
            firePropertyChange("model", old, model);
            old=null; //nos aseguramos que el garbage collector se encargue de el mas tarde
        }
    }
    
    
}
