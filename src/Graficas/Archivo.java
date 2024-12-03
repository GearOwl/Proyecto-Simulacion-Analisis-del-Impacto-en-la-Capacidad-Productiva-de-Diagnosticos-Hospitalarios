package Graficas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Archivo {

    private int pacientes_diarios[] = new int[360];
    private int cancelacion_diarios[] = new int[360];
    private int lista_espera_diarios[] = new int[360];
    private int produccion_diarios[] = new int[360];
    private int tiempo_atencion_diarios[] = new int[360];
    private int suma = 0;
    String path;

    public Archivo() {

    }

    public String seleccionarArchivo() {

        // TODO add your handling code here:
        JFileChooser directoryChooser = new JFileChooser();

        // Permitir solo la selección de directorios y archivos
        directoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        // Mostrar el cuadro de diálogo
        int resultado = directoryChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            File directorioSeleccionado = directoryChooser.getSelectedFile();
            path = directorioSeleccionado.getAbsolutePath();
        }
        return path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean leerArchivoCSV(String archivo) {

        int pos = 0;
        File f = new File(archivo);
        if (f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String leer = br.readLine();
                boolean primeraLinea = true;
                while (leer != null) {
                    if (primeraLinea) {
                        primeraLinea = false;
                        leer = br.readLine();
                        continue;
                    }
                    String arr[] = leer.split(",");

                    pacientes_diarios[pos] = Integer.parseInt(arr[0]);
                    produccion_diarios[pos] = Integer.parseInt(arr[1]);
                    lista_espera_diarios[pos] = Integer.parseInt(arr[2]);
                    cancelacion_diarios[pos] = Integer.parseInt(arr[3]);
                    tiempo_atencion_diarios[pos] = Integer.parseInt(arr[4]);

                    pos++;

                    leer = br.readLine();
                }

                br.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existe el archivo");
            return false;
        }
    }

    public int[] getDemandaMensual() {
        int[] sumasPorMes = new int[12];
        int a = 0;
        int b = 30;
        for (int i = 0; i < 12; i++) {
            sumasPorMes[i] = DemandaMes(a, b);
            a += 30;
            b += 30;
        }

        return sumasPorMes;
    }

    public int DemandaMes(int a, int b) {
        suma = 0;
        for (int i = a; i < b; i++) {
            suma += pacientes_diarios[i];
        }
        return suma;
    }

    public int[] getCancelacionMensual() {
        int[] sumasPorMes = new int[12];
        int a = 0;
        int b = 30;
        for (int i = 0; i < 12; i++) {
            sumasPorMes[i] = CancelacionMes(a, b);
            a += 30;
            b += 30;
        }

        return sumasPorMes;
    }

    public int CancelacionMes(int a, int b) {
        suma = 0;
        for (int i = a; i < b; i++) {
            suma += cancelacion_diarios[i];
        }
        return suma;
    }

    public int[] getListaEsperaMensual() {
        int[] sumasPorMes = new int[12];
        int a = 0;
        int b = 30;
        for (int i = 0; i < 12; i++) {
            sumasPorMes[i] = ListaEsperaMes(a, b);
            a += 30;
            b += 30;
        }

        return sumasPorMes;
    }

    public int ListaEsperaMes(int a, int b) {
        suma = 0;
        for (int i = a; i < b; i++) {
            suma += lista_espera_diarios[i];
        }
        return suma;
    }

    public int[] getProduccionMensual() {
        int[] sumasPorMes = new int[12];
        int a = 0;
        int b = 30;
        for (int i = 0; i < 12; i++) {
            sumasPorMes[i] = ProduccionMes(a, b);
            a += 30;
            b += 30;
        }

        return sumasPorMes;
    }

    public int ProduccionMes(int a, int b) {
        suma = 0;
        for (int i = a; i < b; i++) {
            suma += produccion_diarios[i];
        }
        return suma;
    }

    public double[] getTiempoCicloDiario() {
        double[] tiempoCiclo = new double[360];
        for (int i = 0; i < pacientes_diarios.length; i++) {
            if (pacientes_diarios[i] > 0) {
                tiempoCiclo[i] = tiempo_atencion_diarios[i] / pacientes_diarios[i];
            } else {
                tiempoCiclo[i] = 0;
            }
        }
        return tiempoCiclo;
    }

    public float[] getTiempoCicloMensual() {
        float[] tiempoCicloMensual = new float[12];
        int a = 0;
        int b = 30;
        for (int i = 0; i < 12; i++) {
            tiempoCicloMensual[i] = TiempoCicloMes(a, b);
            a += 30;
            b += 30;
        }
        return tiempoCicloMensual;
    }

    public float TiempoCicloMes(int a, int b) {
        float sumaTiempo = 0;
        int sumaPacientes = 0;
        for (int i = a; i < b; i++) {
            sumaTiempo += tiempo_atencion_diarios[i];
            sumaPacientes += pacientes_diarios[i];
        }

        if (sumaPacientes > 0) {
            return sumaTiempo / sumaPacientes;
        } else {
            return 0;
        }
    }

    public int[] getPacientes_diarios() {
        return pacientes_diarios;
    }

    public void setPacientes_diarios(int[] pacientes_diarios) {
        this.pacientes_diarios = pacientes_diarios;
    }

    public int[] getCancelacion_diarios() {
        return cancelacion_diarios;
    }

    public void setCancelacion_diarios(int[] cancelacion_diarios) {
        this.cancelacion_diarios = cancelacion_diarios;
    }

    public int[] getLista_espera_diarios() {
        return lista_espera_diarios;
    }

    public void setLista_espera_diarios(int[] lista_espera_diarios) {
        this.lista_espera_diarios = lista_espera_diarios;
    }

    public int[] getProduccion_diarios() {
        return produccion_diarios;
    }

    public void setProduccion_diarios(int[] produccion_diarios) {
        this.produccion_diarios = produccion_diarios;
    }

    public int[] getTiempo_atencion_diarios() {
        return tiempo_atencion_diarios;
    }

    public void setTiempo_atencion_diarios(int[] tiempo_atencion_diarios) {
        this.tiempo_atencion_diarios = tiempo_atencion_diarios;
    }

}
