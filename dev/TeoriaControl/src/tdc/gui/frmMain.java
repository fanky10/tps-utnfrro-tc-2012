/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmMain.java
 *
 * Created on 13-abr-2011, 20:26:03
 */

package tdc.gui;

import tdc.primer_orden.gui.pnlEntradaEscalon;
import tdc.primer_orden.gui.pnlEntradaImpulso;
import tdc.Configuracion;
import tdc.primer_orden.gui.pnlEntradaSenoidal;

/**
 *
 * @author fanky
 */
public class frmMain extends javax.swing.JFrame {
    //los informes de primer orden
    private pnlEntradaEscalon pnl_escalon;
    private pnlEntradaImpulso pnl_impulso;
    private pnlEntradaSenoidal pnl_senoidal;

    private static final String TITLE = "Teoria de Control "+Configuracion.getVersion();
    /** Creates new form frmMain */
    public frmMain() {
        initComponents();
        setTitle(TITLE);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        agrega_primer_orden();
    }

    private void agrega_primer_orden(){
        tbdPane.removeAll();

        pnl_escalon = new pnlEntradaEscalon();
        tbdPane.add(pnl_escalon.toString(), pnl_escalon);

        pnl_impulso = new pnlEntradaImpulso();
        tbdPane.add(pnl_impulso.toString(),pnl_impulso);

        pnl_senoidal = new pnlEntradaSenoidal();
        tbdPane.add(pnl_senoidal.toString(),pnl_senoidal);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbdPane = new javax.swing.JTabbedPane();
        lblEstado = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblEstado.setText("Estado");
        lblEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setText("1er Orden");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setText("2do Orden");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
            .addComponent(tbdPane, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbdPane, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        agrega_primer_orden();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JTabbedPane tbdPane;
    // End of variables declaration//GEN-END:variables

}