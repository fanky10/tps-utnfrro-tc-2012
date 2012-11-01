/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlEntradaImpulso.java
 *
 * Created on 25-abr-2011, 22:56:39
 */
package tdc.segundo_orden.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tdc.Configuracion;
import tdc.gui.entidades.CustomChartPanel;
import tdc.gui.entidades.DefaultChartModel;
import tdc.gui.frmTable;
import tdc.segundo_orden.entidades.EntradaImpulso;

/**
 *
 * @author facundo
 */
public class pnlEntradaImpulso extends JPanel {

    private CustomChartPanel cPanel;
    private EntradaImpulso entrada;
    private EntradaEscalonOrdenDosForm dataInputCatalog;

    /** Creates new form pnlEscalon */
    public pnlEntradaImpulso() {
        initComponents();
        cPanel = new CustomChartPanel(new DefaultChartModel(EntradaImpulso.CHART_TITLE, "Tiempo", "Y(t)"));
        pnlGrafico.add(cPanel, BorderLayout.CENTER);
    }

    protected void ingresar_datos() {
        DiagIngresoEntradaEscalon diag = new DiagIngresoEntradaEscalon(null, true);
        diag.setVisible(true);
        diag.dispose();
        dataInputCatalog = diag.getDatosIngresados();
        refreshDatos();

    }

    protected void ver_tabla() {
        if (entrada == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debe ingresar datos validos");
        }
        new frmTable(null, true, "Tiempos Asentamiento / Subida", entrada).setVisible(true);
    }

    @Override
    public String toString() {
        return "Entrada Impulso";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pnlGrafico = new javax.swing.JPanel();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setText("Ingresar Datos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton4.setText("Ver Tabla");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        pnlGrafico.setBackground(new java.awt.Color(255, 255, 255));
        pnlGrafico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGrafico.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ingresar_datos();
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ver_tabla();
}//GEN-LAST:event_jButton4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel pnlGrafico;
    // End of variables declaration//GEN-END:variables

    private void refreshDatos() {
        if (dataInputCatalog == null) {
            ingresar_datos();
        }
        entrada = new EntradaImpulso(dataInputCatalog);
        cPanel.setModel(new DefaultChartModel(entrada.getChart()));

    }

    public static void main(String args[]) {
        JFrame frmMain = new JFrame("TeoriaDeControl " + Configuracion.getVersion());
        frmMain.getContentPane().add(new pnlEntradaEscalon());
        frmMain.setLocationRelativeTo(null);
        frmMain.pack();
        frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setVisible(true);
    }
}
