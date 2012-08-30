/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * pnlEntradaEscalon.java
 *
 * Created on 13-abr-2011, 20:32:57
 */

package tdc.primer_orden.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tdc.Configuracion;
import tdc.entidades.DataInputCatalog;
import tdc.gui.entidades.CustomChartPanel;
import tdc.gui.entidades.DefaultChartModel;
import tdc.gui.frmTable;
import tdc.primer_orden.entidades.EntradaEscalon;


/**
 *
 * @author facundo
 */
public class pnlEntradaEscalon extends JPanel{
    
    private CustomChartPanel cPanel;
    private EntradaEscalon entrada;
    /** Creates new form pnlEscalon */
    public pnlEntradaEscalon() {
        initComponents();
        cPanel = new CustomChartPanel(new DefaultChartModel("Rta. a entrada del tipo Escalon","Tiempo","Y(t)"));
        pnlGrafico.add(cPanel,BorderLayout.CENTER);
    }
    
    /**
     * 0.1 ver si actualiza el panel
     * 0.2 generar chart y actualizar el panel
     */
    protected void ingresar_datos() {
        //v0.1
//        String message = javax.swing.JOptionPane.showInputDialog("Ingrese el nombre del nuevo chart");
//        cPanel.setModel(new DefaultChartModel(message,"x","y"));
        //v0.2
        diagIngresoDatos diag = new diagIngresoDatos(null, true,diagIngresoDatos.ENTRADA_ESCALON);
        diag.setVisible(true);
        diag.dispose();
        DataInputCatalog data_cat = diag.getDatosIngresados();
        entrada = new EntradaEscalon(data_cat);
        cPanel.setModel(new DefaultChartModel(entrada.getChart()));
    }
    protected void ver_tabla(){
        if(entrada == null){
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debe ingresar datos validos");
        }
        new frmTable(null, true,"Tiempos Asentamiento / Subida",entrada).setVisible(true);
    }
    
    
    @Override
    public String toString() {
        return "EntradaEscalon";
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlGrafico = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        pnlGrafico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlGrafico.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Ingresar Datos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("Ver Tabla");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGrafico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ingresar_datos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ver_tabla();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlGrafico;
    // End of variables declaration//GEN-END:variables

    public static void main(String args[]){
        JFrame frmMain = new JFrame("TeoriaDeControl "+Configuracion.getVersion());
        frmMain.getContentPane().add(new pnlEntradaEscalon());
        frmMain.setLocationRelativeTo(null);
        frmMain.pack();
        frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMain.setVisible(true);
    }
}
