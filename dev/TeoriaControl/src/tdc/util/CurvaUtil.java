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

    public Double getGraphPeak(DataInput di, double psi, Double maxTime) {
        double result = 0D;
        double valPrev1 = 0D;
        double valPrev2 = 0D;
        boolean isSupPeak = false;
        boolean isInfPeak = false;

        for (double time = 0; time < maxTime; time = time + DataInput.JUMP) {
            Double value = getfdet(di, time, psi);

            if ((value >= valPrev1 && valPrev1 >= valPrev2)
                    || (value <= valPrev1 && valPrev1 <= valPrev2)) {
                valPrev2 = valPrev1;
                valPrev1 = value;

            } else if (value < valPrev1 && valPrev1 >= valPrev2) {
                isSupPeak = true;
                result = value;

            } else if (value > valPrev1 && valPrev1 <= valPrev2) {
                isInfPeak = true;
                result = value;
            }
        }
        if (isSupPeak) {
            System.out.println("se ha encontrado pico sup y es :" + result);
        }
        if (isInfPeak) {
            System.out.println("se ha encontrado pico inf y es :" + result);
        }

        return result;

    }
}
