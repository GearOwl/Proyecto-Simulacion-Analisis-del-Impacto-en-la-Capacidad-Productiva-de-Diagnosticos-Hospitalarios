package Graficas;


import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JPanel;
import Panel.Ventana_Inicio;
import java.util.Arrays;


public class Demanda_Produccion extends JPanel {

    String[] mes = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
    int [] arr_dem = new int[12]; 
    int [] arr_pro = new int[12];
    
    double [] demanda = new double[12];
    double [] produccion = new double[12];
    
    public Demanda_Produccion(){
        this.removeAll();
        this.revalidate(); // This removes the old chart 
        ChartPanel chartPanel = (ChartPanel) crearGrafico();
        chartPanel.setPreferredSize(new Dimension(750, 320)); 
        this.add(chartPanel);
        this.setLayout(new BorderLayout());
        this.add(chartPanel,BorderLayout.NORTH);
      
    }
    private ChartPanel crearGrafico() {
        
        DefaultCategoryDataset datos = setDatos();

        JFreeChart histograma = ChartFactory.createBarChart3D(
                "Demanda y producción mensual de procedimientos en la sala de angiografía", //Nombre del grafico
                "Meses", //nombre de las columnas
                "Cantidad de procedimientos", //nombre de la numeración
                datos, //datos del grafico
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(histograma);
        panel.setPreferredSize(new Dimension(750, 320));
        
        return panel;

    }
    
    private DefaultCategoryDataset setDatos(){
        
        Archivo archivo = new Archivo();
        archivo.leerArchivoCSV(direccionArchivo());
        arr_dem = archivo.getDemandaMensual();
        demanda = Arrays.stream(arr_dem).asDoubleStream().toArray();
        
        arr_pro = archivo.getProduccionMensual();
        produccion = Arrays.stream(arr_pro).asDoubleStream().toArray();
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        for(int i=0;i<12;i++){
            datos.setValue(arr_dem[i],"Demanda",""+mes[i]);
            datos.setValue(arr_pro[i],"Producción",""+mes[i]);
        }
        
        return datos;
    }
    
    private String direccionArchivo(){
        Ventana_Inicio vi = new Ventana_Inicio();
        return vi.getPath();
    }

    public double[] getDemanda() {
        return demanda;
    }

    public void setDemanda(double[] demanda) {
        this.demanda = demanda;
    }

    public double[] getProduccion() {
        return produccion;
    }

    public void setProduccion(double[] produccion) {
        this.produccion = produccion;
    }
    
    
}
