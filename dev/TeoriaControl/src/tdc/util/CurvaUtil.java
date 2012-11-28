/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.util;

import tdc.entidades.DataInput;

/**
 *
 * @author mario.bedin
 */
public abstract class CurvaUtil {

    public abstract double getfdet(DataInput di, double time, Double psi);
    public abstract double getBandaSuperior(DataInput di,Double porcAsentamiento);
    public abstract double getBandaInferior(DataInput di,Double porcAsentamiento);
    
    public double getPrimerPicoBetween(DataInput di, double psi, Double maxTime, Double porcAsentamiento) {
        double beforeValue = 0;
        boolean estaBajando = false;
        boolean estaSubiendo = false;
        Double bandaSup = getBandaSuperior(di, porcAsentamiento);
        Double bandaInf = getBandaInferior(di, porcAsentamiento);
        Double result = 0D;
        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            //get function value
            double currentFunctionValue = getfdet(di, time, psi);
            
            //check if pico superior
            boolean esPicoSuperior = esPicoSuperior(currentFunctionValue, beforeValue, di.getAmplitud());
            if (esPicoSuperior && !estaBajando) {
                estaBajando = true;
                if (currentFunctionValue >= bandaInf && currentFunctionValue <= bandaSup) {
                    result = time;
                    break;
                }

            } else if (!esPicoSuperior) {
                estaBajando = false;
            }
            
            //check if pico inf
            boolean esPicoInferior = esPicoInferior(currentFunctionValue, beforeValue, di.getAmplitud());
            if (esPicoInferior && !estaSubiendo) {
                estaSubiendo = true;
                if (currentFunctionValue >= bandaInf && currentFunctionValue <= bandaSup) {
                    result = time;
                    break;
                }
            } else if (!esPicoInferior) {
                estaSubiendo = false;
            }

            beforeValue = currentFunctionValue;//sobreescribimos el valor

        }
        return result;
    }

    private boolean esPicoSuperior(Double currentValue, Double beforeValue, Double trasehold) {
        return currentValue < beforeValue && currentValue > trasehold;
    }

    private boolean esPicoInferior(Double currentValue, Double beforeValue, Double trasehold) {
        return currentValue > beforeValue && currentValue < trasehold;
    }
}
