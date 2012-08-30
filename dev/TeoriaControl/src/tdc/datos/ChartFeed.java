/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.datos;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author fanky
 */
public class ChartFeed implements Runnable{
    public static double A = 5D;
    public static double TAU = 6D;
    public static double JUMP = 5D;
    private int delay;//milisecs
    private FeedHandler handler;
    public ChartFeed(int delay, FeedHandler handler){
        this.delay = delay;
        this.handler = handler;
    }

    public void start(){
        new Thread(this, "My Feeder").start();
    }
    public void run() {
        for(double time =0;time<JUMP*TAU;time=time+TAU){
            double ydt =( A*(1-Math.pow (Math.E,(-time/TAU))));
            System.out.println("Y(t) generado: "+ydt);
            FeedEvent evt = new FeedEvent(this);
            evt.setData(ydt);
            fireEvent(evt);
            try{
                Thread.sleep((long)delay);
            }catch(InterruptedException ex){
                //ignore..
                System.out.println("interrupted exception ex: "+ex.getMessage());
            }
        }
    }
    private void fireEvent(FeedEvent evt){
        if(handler!=null){
            handler.eventChanged(evt);
        }
    }
}