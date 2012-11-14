/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdc.util;

import java.awt.Color;
import tdc.entidades.DataInput;

/**
 *
 * @author Fanky10 <fanky10@gmail.com>
 */
public class TestCurvaUtil {

    public void test() {
        double psi = 0.2D;
        double amplitud = 1;
        double tau = 1;
        double maxTime = 30*tau;
        DataInput di = new DataInput("Datos1", amplitud, tau, Color.red);
        
        CurvaUtil cu = new CurvaUtil() {

            @Override
            public double getfdet(DataInput di, double time, Double psi) {
                Double result = 0D;
                //psi >0
                if (psi < 0) {
                    throw new IllegalArgumentException("invalid psi value: " + psi);
                }
                if (psi == 1) {
                    Double t1First = 1 + time / di.getTau();
                    debug("t1First: " + t1First);
                    Double t1Second = Math.pow(Math.E, (-time / di.getTau()));
                    debug("t1Second: " + t1Second);

                    result = 1 - t1First * t1Second;

                } else if (psi < 1) {
                    Double t1First = 1 / (Math.sqrt(1 - Math.pow(psi, 2)));
                    Double t1Second = Math.pow(Math.E, ((-psi * time) / di.getTau()));
                    Double secondTerm1 = t1First * t1Second;
                    debug("secondTerm1: " + secondTerm1);

                    Double sinFirst = Math.sqrt(1 - Math.pow(psi, 2)) * time / di.getTau();
                    Double sinSecond = Math.atan(Math.sqrt(1 - Math.pow(psi, 2)) / psi);
                    Double secondTerm2 = Math.sin(sinFirst + sinSecond);
                    debug("secondTerm2: " + secondTerm2);

                    result = 1 - secondTerm1 * secondTerm2;
                } else { //psi > 1 :P
                    Double t1First = Math.pow(Math.E, ((-psi * time) / di.getTau()));
                    debug("t1First: " + t1First);
                    Double coshFirst = Math.cosh(Math.sqrt(Math.pow(psi, 2) - 1) * (time / di.getTau()));
                    debug("coshFirst: " + coshFirst);
                    Double t1Second = psi / (Math.sqrt(Math.pow(psi, 2) - 1));
                    debug("t1Second: " + t1Second);
                    Double sinhFirst = Math.sinh(Math.sqrt(Math.pow(psi, 2) - 1) * (time / di.getTau()));
                    debug("sinhFirst: " + sinhFirst);
                    debug("generado??: " + (t1First * (coshFirst + t1Second * sinhFirst)));
                    result = 1 - t1First * (coshFirst + t1Second * sinhFirst);
                }

                debug("Y(" + time + ") generado: " + (di.getAmplitud() * result));
                debug("-----------------");
                return di.getAmplitud() * result;
            }
        };
//        debug("generating Graphic tau: " + di.getTau() + " amplitud: " + di.getAmplitud());
//        debug("psi: " + psi);
//        double tiempoUltimoPeak = cu.getGraphPeak(di, psi, maxTime);
//        debug("ultimo pico: " + tiempoUltimoPeak);
//        cu.showPicosSuperiores(di, psi, maxTime);
//        cu.showPicosInferiores(di, psi, maxTime);
//        cu.showAllPicos(di, psi, maxTime);
//        cu.showAllPicosInBetween(di, psi, maxTime, 5D);
        debug("primerPico en banda: "+cu.getPrimerPicoBetween(di, psi, maxTime, 5D));
    }

    public static void main(String args[]) {
        TestCurvaUtil test = new TestCurvaUtil();
        test.test();
    }

    public static void debug(String txt) {
        if (true) {
            System.out.println("DEBUG: " + txt);
        }
    }
}
