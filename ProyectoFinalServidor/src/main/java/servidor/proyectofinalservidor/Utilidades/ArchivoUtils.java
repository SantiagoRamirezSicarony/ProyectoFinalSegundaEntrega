package servidor.proyectofinalservidor.Utilidades;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtils {
    static String fechaSistema = "";
    private static String fechaSistemaCompleta="";

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     * @param ruta es la ruta o path donde esta ubicado el archivo
     * @throws IOException
     */
    public static void guardarArchivo(String ruta,String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }
    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo)
    {
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler =  null;
        cargarFechaSistema();

        // Modificar la ruta del archivo para incluir la fecha
        String rutaArchivoConFecha = rutaArchivo + fechaSistema + ".log";

        try {
            fileHandler = new FileHandler(rutaArchivoConFecha, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistemaCompleta);
                    break;

                case 2:
                    LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistemaCompleta);
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistemaCompleta);
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }

    private static void cargarFechaSistema() {
        String diaN = "";
        String mesN = "";
        String añoN = "";

        Calendar cal1 = Calendar.getInstance();

        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int año = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR_OF_DAY);
        int minuto = cal1.get(Calendar.MINUTE);

        if (dia < 10) {
            diaN += "0" + dia;
        } else {
            diaN += "" + dia;
        }
        if (mes < 10) {
            mesN += "0" + mes;
        } else {
            mesN += "" + mes;
        }

        fechaSistema = año + "-" + mesN + "-" + diaN;
        fechaSistemaCompleta = año + "-" + mesN + "-" + diaN + " " + hora + "_" +minuto;
    }
    /**
     * Copia todos los archivos y carpetas de una carpeta origen a una carpeta destino con un nuevo nombre.
     *
     * @param origen La ruta de la carpeta origen.
     * @param destinoBase La ruta de la carpeta en la que se creará la carpeta destino.
     * @throws IOException Si ocurre un error durante la copia.
     */
    public static void copiarCarpetaConNuevoNombre(Path origen, Path destinoBase) throws IOException {
        // Crear la carpeta destino con el nuevo nombre en la ubicación de destinoBase
        cargarFechaSistema();
        Path destino = destinoBase.resolve(fechaSistemaCompleta);

        // Si el destino (con el nuevo nombre) no existe, lo creamos
        if (Files.notExists(destino)) {
            Files.createDirectories(destino);
        }

        // Caminamos por la estructura de la carpeta origen
        Files.walkFileTree(origen, new SimpleFileVisitor<Path>() {

            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // Creamos cada subdirectorio en la carpeta destino
                Path directorioDestino = destino.resolve(origen.relativize(dir));
                if (Files.notExists(directorioDestino)) {
                    Files.createDirectories(directorioDestino);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Copiamos cada archivo en la carpeta destino
                Path archivoDestino = destino.resolve(origen.relativize(file));
                Files.copy(file, archivoDestino, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
