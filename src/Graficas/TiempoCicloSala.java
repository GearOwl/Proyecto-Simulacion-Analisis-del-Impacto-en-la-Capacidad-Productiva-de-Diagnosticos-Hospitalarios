package Graficas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import Panel.Ventana_Inicio;
import java.awt.Color;
import java.util.Arrays;



public class TiempoCicloSala extends JPanel{

    private double[] array;
    
     public  TiempoCicloSala() {
        this.removeAll();
        this.revalidate(); // This removes the old chart 
        HistogramDataset dataset = new HistogramDataset();
        
         Archivo archivo = new Archivo();
        archivo.leerArchivoCSV(direccionArchivo());
        double[] data = Arrays.stream(archivo.getTiempo_atencion_diarios()).asDoubleStream().toArray();
        array = data;
        
        //double[] array = Arrays.stream(data).asDoubleStream().toArray();
        dataset.addSeries("Frecuencias", data, Math.round(Math.round((1 + 3.33 * Math.log10(data.length))))); // Dividir en 10 intervalos

        JFreeChart histograma = ChartFactory.createHistogram(
            "Tiempo De Ciclo En Sala",  // Título
            "Minutos",              // Etiqueta eje X
            "Pacientes",           // Etiqueta eje Y
            dataset,                // Dataset
            PlotOrientation.VERTICAL, // Orientación del gráfico
            true,                   // Mostrar leyenda
            true,                   // Mostrar tooltips
            false                   // No usar URLs
        );
        
        histograma.setBackgroundPaint(Color.white); // Fondo del gráfico
       histograma.getPlot().setBackgroundPaint(Color.white); // Fondo del área de trazado
        
        ChartPanel panel = new ChartPanel(histograma);
        panel.setPreferredSize(new Dimension(750,320));
        this.add(panel);
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.NORTH);
    }
    
    
    private String direccionArchivo(){
        Ventana_Inicio vi = new Ventana_Inicio();
        return vi.getPath();
    }

    public double[] getArray() {
        return array;
    }

    public void setArray(double[] array) {
        this.array = array;
    }
    
    
}

