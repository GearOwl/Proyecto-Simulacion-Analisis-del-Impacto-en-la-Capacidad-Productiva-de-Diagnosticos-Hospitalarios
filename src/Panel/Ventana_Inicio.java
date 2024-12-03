
package Panel;

import Graficas.Archivo;


public class Ventana_Inicio extends javax.swing.JPanel{
    
    public static String path="";
                
    public Ventana_Inicio() {
        initComponents();
    }

    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabelDemPro = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(250, 244, 244));
        setPreferredSize(new java.awt.Dimension(818, 528));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("<html>"
            + "<p>Es una herramienta de simulación diseñada para optimizar procesos diagnósticos. A través de su interfaz intuitiva, "
            + "permite analizar y proyectar datos críticos para mejorar la eficiencia y la atención al paciente en áreas clave como la sala de angiografía.</p>"
            + "<ul>"
            + "<li>Muestra la cantidad de procedimientos realizados en la sala de angiografía y la demanda mensual. "
            + "Incluye gráficos que permiten una visualización clara de la eficiencia y capacidad productiva.</li>"
            + "<li>Muestra el crecimiento mensual de pacientes en espera. Los datos pueden ser ingresados manualmente o cargados "
            + "desde un archivo CSV, el cual se lee automáticamente para generar gráficos.</li>"
            + "<li>Visualiza la frecuencia y cantidad de pacientes que llegan al hospital en un periodo de tiempo determinado.</li>"
            + "<li>Permite monitorear el tiempo promedio que los pacientes permanecen en la sala de diagnóstico, "
            + "ayudando a identificar áreas de mejora en los flujos de trabajo.</li>"
            + "<li>Representa el modelo completo de simulación, incluyendo proyecciones de eficiencia y análisis de carga de trabajo.</li>"
            + "</ul>"
            + "</html>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 69, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 58, Short.MAX_VALUE))
        );

        jLabelDemPro.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDemPro.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelDemPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/produccion_1.png"))); // NOI18N
        jLabelDemPro.setText("Acerca del Proyecto");
        jLabelDemPro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabelDemPro.setOpaque(true);

        jButton1.setText("Cargar Archivo CSV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelDemPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(jButton1)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabelDemPro)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Archivo arch = new Archivo();
       path = arch.seleccionarArchivo();
    }//GEN-LAST:event_jButton1ActionPerformed

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabelDemPro;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
