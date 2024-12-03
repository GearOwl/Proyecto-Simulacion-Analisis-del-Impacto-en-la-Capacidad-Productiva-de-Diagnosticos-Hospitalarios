
package Graficas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;


public class PruebasDeBondad {
    public static double normal(double[] data) {
        // Parámetros de la distribución normal esperada (media y desviación estándar)
        double mean = 7;
        double stdDev = 0.5;

        // Crear la distribución normal esperada
        NormalDistribution normalDistribution = new NormalDistribution(mean, stdDev);

        // Prueba de Kolmogorov-Smirnov
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(normalDistribution, data);

        return pValue;
    }

   public static double triangular(double[]data) {
        // Simular una muestra grande de tamaño 360
        
        // Calcular el modo de los datos
        List<Double> modes = calculateMode(data);

        // Parámetros de la distribución triangular
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        double a = stats.getMin();
        double b = stats.getMax();
        double c = modes.get(0); // Usar el modo como el parámetro c

        // Realizar prueba de bondad Kolmogorov-Smirnov
        double ksStatistic = kolmogorovSmirnovTest(data, a, b, c);

        return ksStatistic;
    }


   

    public static double kolmogorovSmirnovTest(double[] data, double a, double b, double c) {
        // Ordenar los datos
        Arrays.sort(data);

        int n = data.length;
        double dMax = 0;

        // Iterar sobre los datos y calcular las diferencias máximas
        for (int i = 0; i < n; i++) {
            double empiricalCDF = (double) (i + 1) / n; // CDF empírica
            double theoreticalCDF = triangularCDF(data[i], a, b, c); // CDF teórica

            // Calcular diferencias
            double dPlus = Math.abs(empiricalCDF - theoreticalCDF);
            double dMinus = Math.abs(theoreticalCDF - (double) i / n);

            // Actualizar máximo
            dMax = Math.max(dMax, Math.max(dPlus, dMinus));
        }

        return dMax;
    }

    public static double triangularCDF(double x, double a, double b, double c) {
        if (x < a) return 0.0;
        if (x >= a && x < c) {
            return Math.pow(x - a, 2) / ((b - a) * (c - a));
        }
        if (x >= c && x <= b) {
            return 1 - Math.pow(b - x, 2) / ((b - a) * (b - c));
        }
        return 1.0;
    }
      
    
     public static double exponencial(double [] data) {
        // Datos observados (tu muestra)
        

        // Estimación de lambda (tasa media de ocurrencias)
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        double mean = stats.getMean();
        double lambda = 1.0 / mean;

        // Crear la distribución exponencial con el parámetro estimado
        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(lambda);

        // Prueba de Kolmogorov-Smirnov
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(exponentialDistribution, data);

        return pValue;
    }
     
     public static double gammmma(double [] data) {
        // Datos observados (tu muestra)
        

        // Calcular media y varianza
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        double mean = stats.getMean();
        Variance v = new Variance();
        double variance = v.evaluate(data);

        // Estimar parámetros k y theta
        double shape = mean * mean / variance; // k
        double scale = variance / mean;       // θ

        // Crear la distribución Gamma con los parámetros estimados
        GammaDistribution gammaDistribution = new GammaDistribution(shape, scale);

        // Prueba de Kolmogorov-Smirnov
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(gammaDistribution, data);

       return pValue;
     }
     
     public static double weeeiibull(double[] data) {
        // Datos observados (tu muestra)
       

        // Estimación de parámetros forma (k) y escala (λ) (ejemplo con media/desviación estándar)
       DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        double mean = stats.getMean();
        double stdDev = stats.getStandardDeviation();
        
        // Parámetros iniciales para Weibull (pueden mejorarse con ajustes numéricos)
        double shape = (mean / stdDev) * Math.sqrt(Math.PI / 2);; // Forma (k) aproximada
        double scale = mean / Math.pow(Math.sqrt(Math.PI / 2), 1.0 / shape); // Escala (λ) inicial

        // Crear la distribución Weibull con los parámetros estimados
        WeibullDistribution weibullDistribution = new WeibullDistribution(shape, scale);

        // Prueba de Kolmogorov-Smirnov
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(weibullDistribution, data);

        return pValue;
    }
     
     public static double betaaaa(double[] data) {
        // Datos observados (deben estar en el rango [0, 1])
        

        // Calcular media y varianza
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        double mean = stats.getMean();
        Variance v = new Variance();
        double variance = v.evaluate(data);

        // Estimar parámetros α y β
        double alpha = mean * ((mean * (1 - mean)) / variance - 1);
        double beta = (1 - mean) * ((mean * (1 - mean)) / variance - 1);

        // Crear la distribución Beta con los parámetros estimados
        BetaDistribution betaDistribution = new BetaDistribution(alpha, beta);

        // Prueba de Kolmogorov-Smirnov
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(betaDistribution, data);

        return pValue;
    }
     

      public static List<Double> calculateMode(double[] data) {
        // Mapa para contar las frecuencias
        Map<Double, Integer> frequencyMap = new HashMap<>();

        // Contar frecuencias
        for (double num : data) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Encontrar la frecuencia máxima
        int maxFrequency = Collections.max(frequencyMap.values());

        // Extraer los valores con frecuencia máxima
        List<Double> modes = new ArrayList<>();
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }

        return modes;
    }
}
