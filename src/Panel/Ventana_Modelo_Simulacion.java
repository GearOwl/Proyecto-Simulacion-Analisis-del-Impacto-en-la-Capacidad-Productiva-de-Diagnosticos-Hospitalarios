package Panel;

import Graficas.Archivo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Ventana_Modelo_Simulacion extends javax.swing.JPanel {

    Archivo arch = new Archivo();

    public Ventana_Modelo_Simulacion() {

        initComponents();

        arch.leerArchivoCSV(direccionArchivo());
        double[] data_produccion = Arrays.stream(arch.getProduccion_diarios()).asDoubleStream().toArray();
        DescriptiveStatistics datos_produccion = new DescriptiveStatistics();
        for (double value : data_produccion) {
            datos_produccion.addValue(value);
        }

        capacidadDiaria.setText("" + Math.round(datos_produccion.getMean()));
        dias_Simulacion.setText("30");
        panelGrafico.removeAll();
        panelGrafico.revalidate(); 
        JPanel histograma = SimulacionDemandaOferta();
        panelGrafico.setLayout(new BorderLayout());
        panelGrafico.add(histograma, BorderLayout.NORTH);
        //pack();
        repaint();
    }

    public JPanel SimulacionDemandaOferta() {

        double[] data = Arrays.stream(arch.getPacientes_diarios()).asDoubleStream().toArray();

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }

        JPanel panel = new JPanel();
        // Configuración de simulación
        int diasSimulacion = 30; // Días de simulación
        int cap_Diaria = 1;

        try {
           diasSimulacion = Integer.parseInt(dias_Simulacion.getText());

            if (diasSimulacion < 30 || diasSimulacion > 360) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero positivo entre 30 y 360");
                diasSimulacion = 30;
                dias_Simulacion.setText("30");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, Ingrese un número entero");
        }
        
        
        try {
            cap_Diaria = Integer.parseInt(capacidadDiaria.getText());

            if (cap_Diaria < 1) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero positivo arriba de 1");
                cap_Diaria = 1;
                capacidadDiaria.setText("1");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, Ingrese un número entero");
            capacidadDiaria.setText("1");
        }

        //int cap_Diaria = Integer.parseInt(capacidadDiaria.getText()); // Capacidad diaria fija del sistema
        double tasaLlegadaPromedio = Math.round(stats.getMean()); // Media de llegadas de pacientes por día
        double desviacionEstandar = 1; // Desviación estándar de llegadas

        // Generar datos de simulación
        List<Integer> demandaAcumulada = new ArrayList<>();
        List<Integer> ofertaAcumulada = new ArrayList<>();
        generarDatosSimulacion(diasSimulacion, cap_Diaria, tasaLlegadaPromedio, desviacionEstandar, demandaAcumulada, ofertaAcumulada);

        panelGrafico.removeAll();
        panelGrafico.revalidate(); 
        // Crear la gráfica
        ChartPanel chartPanel = crearGrafico(demandaAcumulada, ofertaAcumulada, diasSimulacion);
        chartPanel.setPreferredSize(new Dimension(750, 370));

        // Configuración del panel
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    private void generarDatosSimulacion(int diasSimulacion, int capacidadDiaria, double tasaLlegadaPromedio, double desviacionEstandar, List<Integer> demanda, List<Integer> oferta) {
        int pacientesTotales = 0;
        int capacidadTotal = 0;
        Random random = new Random();

        for (int dia = 1; dia <= diasSimulacion; dia++) {
            double pacientesHoy = generarBoxMuller(tasaLlegadaPromedio, desviacionEstandar, random);
            pacientesHoy = Math.max(0, Math.round(pacientesHoy));

            pacientesTotales += (int) pacientesHoy;
            capacidadTotal += capacidadDiaria;

            demanda.add(pacientesTotales);
            oferta.add(capacidadTotal);
        }
    }

    private double generarBoxMuller(double media, double desviacionEstandar, Random random) {
        double u1 = random.nextDouble();
        double u2 = random.nextDouble();

        double z0 = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);
        return media + z0 * desviacionEstandar;
    }

    private ChartPanel crearGrafico(List<Integer> demanda, List<Integer> oferta, int dias) {
        XYSeries serieDemanda = new XYSeries("Demanda (Pacientes)");
        XYSeries serieOferta = new XYSeries("Oferta (Pacientes Atendidos)");

        for (int dia = 1; dia <= dias; dia++) {
            serieDemanda.add(dia, demanda.get(dia - 1));
            serieOferta.add(dia, oferta.get(dia - 1));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(serieDemanda);
        dataset.addSeries(serieOferta);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Demanda vs Oferta",
                "Días",
                "Pacientes",
                dataset
        );
        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.setBackgroundPaint(Color.white);

        return new ChartPanel(chart);
    }

    private String direccionArchivo() {
        Ventana_Inicio vi = new Ventana_Inicio();
        return vi.getPath();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelDemPro = new javax.swing.JLabel();
        panelGrafico = new javax.swing.JPanel();
        capacidadDiaria = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        simular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dias_Simulacion = new javax.swing.JTextField();

        setBackground(new java.awt.Color(250, 244, 244));
        setPreferredSize(new java.awt.Dimension(600, 445));

        jLabelDemPro.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDemPro.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabelDemPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/crecimiento_1.png"))); // NOI18N
        jLabelDemPro.setText("Modelo de Simulación");
        jLabelDemPro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabelDemPro.setOpaque(true);

        panelGrafico.setBackground(new java.awt.Color(255, 255, 255));
        panelGrafico.setPreferredSize(new java.awt.Dimension(750, 370));

        javax.swing.GroupLayout panelGraficoLayout = new javax.swing.GroupLayout(panelGrafico);
        panelGrafico.setLayout(panelGraficoLayout);
        panelGraficoLayout.setHorizontalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelGraficoLayout.setVerticalGroup(
            panelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        capacidadDiaria.setText("4");
        capacidadDiaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capacidadDiariaActionPerformed(evt);
            }
        });

        jLabel1.setText("Capacidad Diaria:");

        simular.setText("Simular");
        simular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simularActionPerformed(evt);
            }
        });

        jLabel2.setText("Días De Simulación:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelDemPro, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(capacidadDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dias_Simulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simular)
                        .addGap(162, 162, 162))
                    .addComponent(panelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabelDemPro)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capacidadDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(simular)
                    .addComponent(jLabel2)
                    .addComponent(dias_Simulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void capacidadDiariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capacidadDiariaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_capacidadDiariaActionPerformed

    private void simularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simularActionPerformed
        // TODO add your handling code here:
        panelGrafico.removeAll();
        panelGrafico.revalidate(); // This removes the old chart 
        JPanel histograma = SimulacionDemandaOferta();
        panelGrafico.setLayout(new BorderLayout());
        //panelGrafico.setPreferredSize(new Dimension(495,234));
        panelGrafico.add(histograma, BorderLayout.NORTH);
        //pack();
        repaint();

    }//GEN-LAST:event_simularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField capacidadDiaria;
    private javax.swing.JTextField dias_Simulacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelDemPro;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JButton simular;
    // End of variables declaration//GEN-END:variables
}
