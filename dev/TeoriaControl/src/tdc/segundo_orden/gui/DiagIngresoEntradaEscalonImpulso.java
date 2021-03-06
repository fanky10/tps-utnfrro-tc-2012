/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * diag.java
 *
 * Created on Oct 29, 2012, 9:56:44 PM
 */
package tdc.segundo_orden.gui;

import java.awt.Color;
import java.util.ArrayList;
import tdc.Utilidades;
import tdc.entidades.DataInput;

/**
 *
 * @author fanky
 */
public class DiagIngresoEntradaEscalonImpulso extends javax.swing.JDialog {

    public static Boolean CANCELADO = true;

    /** Creates new form diag */
    public DiagIngresoEntradaEscalonImpulso(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        rb5a1t.setSelected(true);
        cambia_label();
        setLocationRelativeTo(null);
        defaultValues();
    }

    private void defaultValues() {
        Utilidades.setDouble(txtCtei1, 0.2D);
        Utilidades.setDouble(txtCtei2, 0.4D);
        Utilidades.setDouble(txtCtei3, 0.8D);
        Utilidades.setDouble(txtCtei4, 1.2D);
        Utilidades.setDouble(txtCtei5, 1.4D);
        Utilidades.setDouble(txtCtej1, 1D);
        Utilidades.setDouble(txtAmplitud, 1D);
        Utilidades.setDouble(txtPorcentajeAsentamiento, 5);

    }

    private void cambia_label() {
        if (rb5a1t.isSelected()) {
            lbl5Var.setText("Ingrese Psi");
            lbl1Var.setText("Ingrese Constante de TIEMPO");
        } else if (rb5t1a.isSelected()) {
            lbl5Var.setText("Ingrese Constantes de TIEMPO");
            lbl1Var.setText("Ingrese Psi");
        } else {
            throw new IllegalArgumentException("no possible radiobutton selected!");
        }
    }

    public EntradaEscalonImpulsoOrdenDosForm getDatosIngresados() {
        EntradaEscalonImpulsoOrdenDosForm data = new EntradaEscalonImpulsoOrdenDosForm();
        double var1 = Utilidades.getDouble(txtCtei1);
        double var2 = Utilidades.getDouble(txtCtei2);
        double var3 = Utilidades.getDouble(txtCtei3);
        double var4 = Utilidades.getDouble(txtCtei4);
        double var5 = Utilidades.getDouble(txtCtei5);

        double var6 = Utilidades.getDouble(txtCtej1);

        double amplitud = Utilidades.getDouble(txtAmplitud);

        double porcAsentamiento = Utilidades.getDouble(txtPorcentajeAsentamiento);
        java.util.List<Double> psiV = new ArrayList<Double>();
        if (rb5a1t.isSelected()) {
            //todo: VALIDAR VALOR DE PSI
            psiV.add(var1);
            psiV.add(var2);
            psiV.add(var3);
            psiV.add(var4);
            psiV.add(var5);
            data.add(new DataInput("Datos1", amplitud, var6, Color.red));
        } else if (rb5t1a.isSelected()) {
            psiV.add(var6);
            data.add(new DataInput("Datos1", amplitud, var1, Color.red));
            data.add(new DataInput("Datos2", amplitud, var2, Color.blue));
            data.add(new DataInput("Datos3", amplitud, var3, Color.green));
            data.add(new DataInput("Datos4", amplitud, var4, Color.orange));
            data.add(new DataInput("Datos5", amplitud, var5, Color.yellow));
        } else {
            //throw new IllegalArgumentException("no possible radiobutton selected!");
        }
        data.setPsi(psiV);
        data.setPorcentajeAsentamiento(porcAsentamiento);
        data.autoSort();

        return data;
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
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtCtei1 = new javax.swing.JTextField();
        txtCtei2 = new javax.swing.JTextField();
        txtCtei3 = new javax.swing.JTextField();
        txtCtei4 = new javax.swing.JTextField();
        txtCtei5 = new javax.swing.JTextField();
        lbl5Var = new javax.swing.JLabel();
        txtCtej1 = new javax.swing.JTextField();
        lbl1Var = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rb5a1t = new javax.swing.JRadioButton();
        rb5t1a = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtAmplitud = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtPorcentajeAsentamiento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        txtCtei1.setText("Cte1");
        txtCtei1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtei1FocusGained(evt);
            }
        });
        jPanel1.add(txtCtei1);

        txtCtei2.setText("Cte2");
        txtCtei2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtei2FocusGained(evt);
            }
        });
        jPanel1.add(txtCtei2);

        txtCtei3.setText("Cte3");
        txtCtei3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtei3FocusGained(evt);
            }
        });
        jPanel1.add(txtCtei3);

        txtCtei4.setText("Cte4");
        txtCtei4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtei4FocusGained(evt);
            }
        });
        jPanel1.add(txtCtei4);

        txtCtei5.setText("Cte5");
        txtCtei5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtei5FocusGained(evt);
            }
        });
        jPanel1.add(txtCtei5);

        lbl5Var.setText("Constantes");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lbl5Var, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(lbl5Var)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        txtCtej1.setText("Cte1");
        txtCtej1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtej1FocusGained(evt);
            }
        });

        lbl1Var.setText("Constante");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rb5a1t);
        rb5a1t.setText("5Psi - 1Tiempo");
        rb5a1t.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb5a1tActionPerformed(evt);
            }
        });
        jPanel2.add(rb5a1t);

        buttonGroup1.add(rb5t1a);
        rb5t1a.setText("5Tiempo - 1Psi");
        rb5t1a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb5t1aActionPerformed(evt);
            }
        });
        jPanel2.add(rb5t1a);

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jLabel3.setText("Amplitud");

        txtAmplitud.setText("Psi");

        jLabel1.setText("Asentamiento (%)");

        txtPorcentajeAsentamiento.setText("jTextField1");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtCtej1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(txtAmplitud, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(lbl1Var, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(txtPorcentajeAsentamiento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(lbl1Var)
                .add(3, 3, 3)
                .add(txtCtej1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtAmplitud, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtPorcentajeAsentamiento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCtei1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei1FocusGained

        txtCtei1.selectAll();     }//GEN-LAST:event_txtCtei1FocusGained

    private void txtCtei2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei2FocusGained

        txtCtei2.selectAll();     }//GEN-LAST:event_txtCtei2FocusGained

    private void txtCtei3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei3FocusGained

        txtCtei3.selectAll();     }//GEN-LAST:event_txtCtei3FocusGained

    private void txtCtei4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei4FocusGained

        txtCtei4.selectAll();     }//GEN-LAST:event_txtCtei4FocusGained

    private void txtCtei5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei5FocusGained

        txtCtei5.selectAll();     }//GEN-LAST:event_txtCtei5FocusGained

    private void txtCtej1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtej1FocusGained

        txtCtej1.selectAll();     }//GEN-LAST:event_txtCtej1FocusGained

    private void rb5a1tActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb5a1tActionPerformed

        cambia_label();     }//GEN-LAST:event_rb5a1tActionPerformed

    private void rb5t1aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb5t1aActionPerformed

        cambia_label();     }//GEN-LAST:event_rb5t1aActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CANCELADO = false;
        dispose();     }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DiagIngresoEntradaEscalonImpulso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiagIngresoEntradaEscalonImpulso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiagIngresoEntradaEscalonImpulso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiagIngresoEntradaEscalonImpulso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DiagIngresoEntradaEscalonImpulso dialog = new DiagIngresoEntradaEscalonImpulso(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl1Var;
    private javax.swing.JLabel lbl5Var;
    private javax.swing.JRadioButton rb5a1t;
    private javax.swing.JRadioButton rb5t1a;
    private javax.swing.JTextField txtAmplitud;
    private javax.swing.JTextField txtCtei1;
    private javax.swing.JTextField txtCtei2;
    private javax.swing.JTextField txtCtei3;
    private javax.swing.JTextField txtCtei4;
    private javax.swing.JTextField txtCtei5;
    private javax.swing.JTextField txtCtej1;
    private javax.swing.JTextField txtPorcentajeAsentamiento;
    // End of variables declaration//GEN-END:variables
}
