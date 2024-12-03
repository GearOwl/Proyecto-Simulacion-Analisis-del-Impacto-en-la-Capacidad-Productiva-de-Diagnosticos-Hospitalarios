package Graficas;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import Panel.Ventana_Inicio;
import java.awt.Font;
import org.jfree.chart.axis.SymbolAxis;

public class Lista_Espera extends JPanel {

    private JFreeChart chart;
    double[] data = new double[12];
    String[] mes = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    public Lista_Espera() {
        // Se declara el gráfico XY Lineal
        this.setBackground(Color.white);
        XYDataset xydataset = xyDataset();
        chart = ChartFactory.createXYLineChart(
                "Crecimiento Mensual De La Lista De Espera",
                "Mes",
                "Atenciones",
                xydataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Personalización del gráfico
        XYPlot xyplot = (XYPlot) chart.getPlot();
        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainGridlinePaint(Color.BLACK);
        xyplot.setRangeGridlinePaint(Color.BLACK);

        SymbolAxis domainAxis = new SymbolAxis("Mes", mes);
        domainAxis.setTickMarksVisible(true);
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        domainAxis.setGridBandsVisible(false); // Opcional: desactivar bandas de cuadrícula
        xyplot.setDomainAxis(domainAxis);
    
        // Pinta Shapes en los puntos dados por el XYDataset
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        renderer.setBaseShapesVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(750, 320));
        this.add(chartPanel);
        
    }

    private XYDataset xyDataset() {
        
        XYSeries sEgresos = new XYSeries("Crecimiento");
        int sum = 0;

        Archivo archivo = new Archivo();
        archivo.leerArchivoCSV(direccionArchivo());
        int lista_espera[] = archivo.getListaEsperaMensual();
        for (int i = 0; i < 12; i++) {
            sum += lista_espera[i];
            data[i] = sum;
            sEgresos.add(i, sum);
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();

        xyseriescollection.addSeries(sEgresos);

        return xyseriescollection;
    }

    private String direccionArchivo() {
        Ventana_Inicio vi = new Ventana_Inicio();
        return vi.getPath();
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

}
