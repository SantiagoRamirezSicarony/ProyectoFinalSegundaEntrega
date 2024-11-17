package servidor.proyectofinalservidor.Utilidades;

import servidor.proyectofinalservidor.Modelos.Administrador;
import servidor.proyectofinalservidor.Modelos.Persona;
import servidor.proyectofinalservidor.Modelos.Producto;
import servidor.proyectofinalservidor.Modelos.Vendedor;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Properties;

public class Persistencia {

    public  static Properties properties =  new Properties();

    public static void guardarObjetosTxt(ArrayList<?>lista) throws IOException{
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        String ruta="";
        StringBuilder contenido= new StringBuilder();
        for (Object object: lista){
            contenido.append(object.toString());
        }
        if(lista.get(0) instanceof Administrador){
            ruta=properties.getProperty(Constantes.admins);
        }else if(lista.get(0) instanceof Vendedor){
            ruta= properties.getProperty(Constantes.vendedores);
        }else if(lista.get(0) instanceof Producto){
            ruta= properties.getProperty(Constantes.productos);
        }if (lista.get(0) instanceof Persona){ //unicamente para pruebas
            ruta = properties.getProperty(Constantes.personas);
        }
        ArchivoUtils.guardarArchivo(ruta,contenido.toString() + "\n",false);
    }


    public static void guardarObjetoTxt(Object objeto) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        String ruta="";
        if(objeto instanceof Administrador){
            ruta=properties.getProperty(Constantes.admins);
        }else if(objeto instanceof Vendedor){
            ruta= properties.getProperty(Constantes.vendedores);
        }else if(objeto instanceof Producto){
            ruta= properties.getProperty(Constantes.productos);
        }if (objeto instanceof Persona){
            ruta= properties.getProperty(Constantes.personas);
        }
        ArchivoUtils.guardarArchivo(ruta,objeto.toString() + "\n",true);

    }

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)  {
        try{
            properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
            ArchivoUtils.guardarRegistroLog(mensajeLog, nivel, accion,properties.getProperty(Constantes.archivoLog));
        }catch (Exception e){
            ArchivoUtils.guardarRegistroLog("No se ha encontrado la ruta de properties: "+ e.getMessage(), 3, "Ruta",properties.getProperty(Constantes.archivoLog));
        }

    }

    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        if(!new File(rutaArchivo).exists()){
            return null;
        }

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile(); // Crea el archivo si no existe
        }
        XMLEncoder codificadorXML;
        codificadorXML = new XMLEncoder(new FileOutputStream(archivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

    }


    private static void serializarObjetoBinario(String ruta, Object objeto) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(objeto);
        oos.close();
    }

    /**
     * Metodo que permite deserializar un objeto de un archivo en la ruta especificada
     * @return Objeto deserializado
     * @throws Exception
     */
    private static Object deserializarObjetoBinario(String ruta) throws Exception{

        if(!new File(ruta).exists()){
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = ois.readObject();
        ois.close();


        return objeto;
    }



    // Métodos para listaPersonaEnElSistema
    public static void guardarListaPersonasBin(ArrayList<Persona> listaPersonas) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        serializarObjetoBinario(properties.getProperty(Constantes.personasBin), listaPersonas);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Persona> cargarListaPersonasBin() throws Exception {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        return (ArrayList<Persona>) deserializarObjetoBinario(properties.getProperty(Constantes.personasBin));
    }

    // Métodos para listaAdministradoresSistema
    public static void guardarListaAdministradoresSistemaXML(ArrayList<Administrador> listaAdministradores) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        salvarRecursoSerializadoXML(properties.getProperty(Constantes.adminsXML), listaAdministradores);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Administrador> cargarListaAdministradoresSistemaXML() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        return (ArrayList<Administrador>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.adminsXML));
    }

    // Métodos para listaVendedoresSistema
    public static void guardarListaVendedoresSistemaXML(ArrayList<Vendedor> listaVendedores) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        salvarRecursoSerializadoXML(properties.getProperty(Constantes.vendedoresXML), listaVendedores);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Vendedor> cargarListaVendedoresSistemaXML() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        return (ArrayList<Vendedor>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.vendedoresXML));
    }

    // Métodos para listaProductosSistemaVendidos
    public static void guardarListaProductosVendidosXML(ArrayList<Producto> listaProductosVendidos) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));

        salvarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLvendidos), listaProductosVendidos);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Producto> cargarListaProductosVendidosXML() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        return (ArrayList<Producto>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLvendidos));
    }

    // Métodos para listaProductosSistemaCancelados
    public static void guardarListaProductosCanceladosXML(ArrayList<Producto> listaProductosCancelados) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        salvarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLcancelados), listaProductosCancelados);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Producto> cargarListaProductosCanceladosXML() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        return (ArrayList<Producto>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLcancelados));
    }

    // Métodos para listaProductosSistemaPublicados
    public static void guardarListaProductosPublicadosXML(ArrayList<Producto> listaProductosPublicados) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        salvarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLpublicados), listaProductosPublicados);
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Producto> cargarListaProductosPublicadosXML() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));

        return (ArrayList<Producto>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.productosXMLpublicados));
    }


    public static void generarCopia() throws IOException {
        Path origen = Paths.get("C:/td//ProyectoFinal//Persistencia//Archivos/");
        Path destino = Paths.get("C:/td//ProyectoFinal//Persistencia//Respaldo/");
        ArchivoUtils.copiarCarpetaConNuevoNombre(origen,destino);
    }


    public static void guardarListaProductosXML(ArrayList<Producto> todosLosProductos) throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));
        salvarRecursoSerializadoXML(properties.getProperty(Constantes.productosXML), todosLosProductos);
    }

    public static ArrayList<Producto> cargarTodosLosProductos() throws IOException {
        properties.load(new FileInputStream(new File("src/main/resources/Properties/Rutas.properties")));

        return (ArrayList<Producto>) cargarRecursoSerializadoXML(properties.getProperty(Constantes.productosXML));
    }
}