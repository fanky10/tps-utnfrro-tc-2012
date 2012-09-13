/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc.entidades;

import java.awt.Color;

/**
 *
 * @author fanky
 */
public class DataInput {

    public static double A = 5D;
    public static double TAU = 6D;
    public static double NCTE_TAU_GRAFICA = 5D;
    public static double NCTE_TAU_TABLA = 4D;
    public static double JUMP = 0.03D;
    private double amplitud = A;
    private double tau = TAU;
    private String label;
    private Color color;
    private double valor_base;//equis sub s
    private double frecuencia;
    private double omega;
    public DataInput(String label, double amplitud,double tau,Color color){
        this(label, amplitud, tau, 0D, 0D,color);
    }
    public DataInput(String label, double amplitud,double tau,double valor_base, double frecuencia, Color color) {
        this(label, amplitud, tau, valor_base, frecuencia, Math.round(2*Math.PI*frecuencia), color);
    }
    public DataInput(String label, double amplitud,double tau,double valor_base, double frecuencia,double omega, Color color) {
        this.label = label;
        this.amplitud = amplitud;
        this.tau = tau;
        this.valor_base = valor_base;
        this.frecuencia = frecuencia;
        this.omega = omega;
        this.color=color;
    }

    public double getOmega() {
        return omega;
    }
    public double getPhaseLag(){//phase lag
        return Math.toDegrees(Math.atan(-omega*tau));
    }
    public double getPhaseLag(double tau){//phi
        return Math.toDegrees(Math.atan(-omega*tau));
    }
//    public double getLag(){
//        return Math.abs(getPhaseLag())/(360*frecuencia);
//    }

    public double getAmplitudRta(){
        return amplitud/Math.sqrt(Math.pow(tau, 2)*Math.pow(omega, 2)+1);
    }

    public double getPeriodo(){
        return (2*Math.PI)/omega;
    }

    //<editor-fold desc="getters-setters">
    public double getAmplitud() {
        return amplitud;
    }

    public void setAmplitud(double amplitud) {
        this.amplitud = amplitud;
    }

    public double getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(double frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getLabel() {
        //return label;
        return "[A: "+amplitud+"|T: "+tau+"]";
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }

    public double getValor_base() {
        return valor_base;
    }

    public void setValor_base(double valor_base) {
        this.valor_base = valor_base;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    

}
