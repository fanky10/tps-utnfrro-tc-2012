/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * diagIngresoDatos.java
 *
 * Created on 18-abr-2011, 20:53:41
 */

package tdc.primer_orden.gui;

import java.awt.Color;
import java.util.Collections;
import javax.swing.JTextField;
import tdc.Utilidades;
import tdc.datos.SeriesGenerator;
import tdc.datos.ChartGenerator;
import tdc.entidades.DataInputCatalog;
import tdc.entidades.DataInput;
import tdc.primer_orden.entidades.EntradaEscalon;
import tdc.primer_orden.entidades.EntradaImpulso;

/**
 *
 * @author fanky
 */
public class diagIngresoDatos extends javax.swing.JDialog {
    public static final int ENTRADA_ESCALON = 1;
    public static final int ENTRADA_IMPULSO = 2;
    private int tipo_entrada;
    /** Creates new form diagIngresoDatos */
    public diagIngresoDatos(java.awt.Frame parent, boolean modal,int tipo_entrada) {
        super(parent, modal);
        initComponents();
        this.tipo_entrada=tipo_entrada;
        rb5a1t.setSelected(true);
        cambia_label();
        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        default_values();
    }
    private void cambia_label(){
        if(rb5a1t.isSelected()){
            lbl5Var.setText("Datos de AMPLITUD");
            lbl1Var.setText("Dato de TIEMPO");
        }else if(rb5t1a.isSelected()){
            lbl5Var.setText("Datos de TIEMPO");
            lbl1Var.setText("Dato de AMPLITUD");
        }else{
            throw new IllegalArgumentException("no possible radiobutton selected!");
        }
    }
    private void default_values(){
        Utilidades.setDouble(txtCtei1,1D);
        Utilidades.setDouble(txtCtei2,2D);
        Utilidades.setDouble(txtCtei3,3D);
        Utilidades.setDouble(txtCtei4,4D);
        Utilidades.setDouble(txtCtei5,5D);
        Utilidades.setDouble(txtCtej1,6.5D);

    }
    public DataInputCatalog getDatosIngresados(){
        DataInputCatalog data = new DataInputCatalog();
        double var1 = Utilidades.getDouble(txtCtei1);
        double var2 = Utilidades.getDouble(txtCtei2);
        double var3 = Utilidades.getDouble(txtCtei3);
        double var4 = Utilidades.getDouble(txtCtei4);
        double var5 = Utilidades.getDouble(txtCtei5);
        double var6 = Utilidades.getDouble(txtCtej1);
        if(rb5a1t.isSelected()){
            data.add(new DataInput("Datos1",var1,var6,Color.red));
            data.add(new DataInput("Datos2",var2,var6,Color.blue));
            data.add(new DataInput("Datos3",var3,var6,Color.green));
            data.add(new DataInput("Datos4",var4,var6,Color.orange));
            data.add(new DataInput("Datos5",var5,var6,Color.yellow));
        }else if(rb5t1a.isSelected()){
            data.add(new DataInput("Datos1",var6,var1,Color.red));
            data.add(new DataInput("Datos2",var6,var2,Color.blue));
            data.add(new DataInput("Datos3",var6,var3,Color.green));
            data.add(new DataInput("Datos4",var6,var4,Color.orange));
            data.add(new DataInput("Datos5",var6,var5,Color.yellow));
        }else{
            //throw new IllegalArgumentException("no possible radiobutton selected!");
        }
//        if(tipo_entrada == ENTRADA_ESCALON){
//            if(rb5a1t.isSelected()){
//                data.add(new DataInput("Datos1",var1,var6,Color.red));
//                data.add(new DataInput("Datos2",var2,var6,Color.blue));
//                data.add(new DataInput("Datos3",var3,var6,Color.green));
//                data.add(new DataInput("Datos4",var4,var6,Color.orange));
//                data.add(new DataInput("Datos5",var5,var6,Color.));
//            }else if(rb5t1a.isSelected()){
//                data.add(new DataInput("Datos1",var6,var1));
//                data.add(new DataInput("Datos2",var6,var2));
//                data.add(new DataInput("Datos3",var6,var3));
//                data.add(new DataInput("Datos4",var6,var4));
//                data.add(new DataInput("Datos5",var6,var5));
//            }else{
//                //throw new IllegalArgumentException("no possible radiobutton selected!");
//            }
//        }else if(tipo_entrada == ENTRADA_IMPULSO){
//            if(rb5a1t.isSelected()){
//                data.add(new DataInput("Datos1",var1,var6));
//                data.add(new DataInput("Datos2",var2,var6));
//                data.add(new DataInput("Datos3",var3,var6));
//                data.add(new DataInput("Datos4",var4,var6));
//                data.add(new DataInput("Datos5",var5,var6));
//            }else if(rb5t1a.isSelected()){
//                data.add(new DataInput("Datos1",var6,var1));
//                data.add(new DataInput("Datos2",var6,var2));
//                data.add(new DataInput("Datos3",var6,var3));
//                data.add(new DataInput("Datos4",var6,var4));
//                data.add(new DataInput("Datos5",var6,var5));
//            }else{
//                //throw new IllegalArgumentException("no possible radiobutton selected!");
//            }
//        }
        Collections.sort(data,data);
        DataInput.TAU_MAX = data.get(0).getTau();
        return data;
    }
    @Deprecated
    public ChartGenerator getGeneradores(){
        ChartGenerator data = new ChartGenerator();
//        double var1 = Utilidades.getDouble(txtCtei1);
//        double var2 = Utilidades.getDouble(txtCtei2);
//        double var3 = Utilidades.getDouble(txtCtei3);
//        double var4 = Utilidades.getDouble(txtCtei4);
//        double var5 = Utilidades.getDouble(txtCtei5);
//        double var6 = Utilidades.getDouble(txtCtej1);
//        if(tipo_entrada == ENTRADA_ESCALON){
//            if(rb5a1t.isSelected()){
//                data.add(new EntradaEscalon("Datos1",var1,var6));
//                data.add(new EntradaEscalon("Datos2",var2,var6));
//                data.add(new EntradaEscalon("Datos3",var3,var6));
//                data.add(new EntradaEscalon("Datos4",var4,var6));
//                data.add(new EntradaEscalon("Datos5",var5,var6));
//            }else if(rb5t1a.isSelected()){
//                data.add(new EntradaEscalon("Datos1",var6,var1));
//                data.add(new EntradaEscalon("Datos2",var6,var2));
//                data.add(new EntradaEscalon("Datos3",var6,var3));
//                data.add(new EntradaEscalon("Datos4",var6,var4));
//                data.add(new EntradaEscalon("Datos5",var6,var5));
//            }else{
//                //throw new IllegalArgumentException("no possible radiobutton selected!");
//            }
//        }else if(tipo_entrada == ENTRADA_IMPULSO){
//            if(rb5a1t.isSelected()){
//                data.add(new EntradaImpulso("Datos1",var1,var6));
//                data.add(new EntradaImpulso("Datos2",var2,var6));
//                data.add(new EntradaImpulso("Datos3",var3,var6));
//                data.add(new EntradaImpulso("Datos4",var4,var6));
//                data.add(new EntradaImpulso("Datos5",var5,var6));
//            }else if(rb5t1a.isSelected()){
//                data.add(new EntradaImpulso("Datos1",var6,var1));
//                data.add(new EntradaImpulso("Datos2",var6,var2));
//                data.add(new EntradaImpulso("Datos3",var6,var3));
//                data.add(new EntradaImpulso("Datos4",var6,var4));
//                data.add(new EntradaImpulso("Datos5",var6,var5));
//            }else{
//                //throw new IllegalArgumentException("no possible radiobutton selected!");
//            }
//        }
//        Collections.sort(data,data);
//        SeriesGenerator.TAU_MAX = data.get(0).getTau();//el mayor de todos es el primero :P
//        for(SeriesGenerator dg: data){
//            dg.generate();
//        }
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
        lbl5Var = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCtei1 = new javax.swing.JTextField();
        txtCtei2 = new javax.swing.JTextField();
        txtCtei3 = new javax.swing.JTextField();
        txtCtei4 = new javax.swing.JTextField();
        txtCtei5 = new javax.swing.JTextField();
        lbl1Var = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtCtej1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        rb5a1t = new javax.swing.JRadioButton();
        rb5t1a = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl5Var.setText("Constantes");

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

        lbl1Var.setText("Constante");

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        txtCtej1.setText("Cte1");
        txtCtej1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCtej1FocusGained(evt);
            }
        });
        jPanel3.add(txtCtej1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rb5a1t);
        rb5a1t.setText("5Amp - 1Tiempo");
        rb5a1t.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb5a1tActionPerformed(evt);
            }
        });
        jPanel2.add(rb5a1t);

        buttonGroup1.add(rb5t1a);
        rb5t1a.setText("5Tiempo - 1Amp");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl5Var, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addComponent(lbl1Var, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl5Var, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbl1Var, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCtei1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei1FocusGained
        txtCtei1.selectAll();
}//GEN-LAST:event_txtCtei1FocusGained

    private void txtCtei2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei2FocusGained
        txtCtei2.selectAll();
}//GEN-LAST:event_txtCtei2FocusGained

    private void txtCtei3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei3FocusGained
        txtCtei3.selectAll();
}//GEN-LAST:event_txtCtei3FocusGained

    private void txtCtei4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei4FocusGained
        txtCtei4.selectAll();
}//GEN-LAST:event_txtCtei4FocusGained

    private void txtCtei5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtei5FocusGained
        txtCtei5.selectAll();
}//GEN-LAST:event_txtCtei5FocusGained

    private void txtCtej1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCtej1FocusGained
        txtCtej1.selectAll();
}//GEN-LAST:event_txtCtej1FocusGained

    private void rb5a1tActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb5a1tActionPerformed
        cambia_label();
}//GEN-LAST:event_rb5a1tActionPerformed

    private void rb5t1aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb5t1aActionPerformed
        cambia_label();
}//GEN-LAST:event_rb5t1aActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
}//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                diagIngresoDatos dialog = new diagIngresoDatos(new javax.swing.JFrame(), true,diagIngresoDatos.ENTRADA_ESCALON);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl1Var;
    private javax.swing.JLabel lbl5Var;
    private javax.swing.JRadioButton rb5a1t;
    private javax.swing.JRadioButton rb5t1a;
    private javax.swing.JTextField txtCtei1;
    private javax.swing.JTextField txtCtei2;
    private javax.swing.JTextField txtCtei3;
    private javax.swing.JTextField txtCtei4;
    private javax.swing.JTextField txtCtei5;
    private javax.swing.JTextField txtCtej1;
    // End of variables declaration//GEN-END:variables

}