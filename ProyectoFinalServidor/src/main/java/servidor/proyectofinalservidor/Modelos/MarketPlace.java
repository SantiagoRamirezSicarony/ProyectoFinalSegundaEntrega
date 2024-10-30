package servidor.proyectofinalservidor.Modelos;

import servidor.proyectofinalservidor.Excepciones.ProductoFueraDeStock;
import servidor.proyectofinalservidor.Utilidades.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class MarketPlace {
    private ArrayList<Administrador> listaAdministradoresSistema;
    private ArrayList<Vendedor> listaVendedoresSistema;
    private ArrayList<Producto> listaProductosSistemaVendidos;
    private ArrayList<Producto> listaProductosSistemaCancelados;
    private ArrayList<Producto> listaProductosSistemaPublicados;
    private ArrayList<Persona> listaPersonaEnElSistema;




    public MarketPlace() {
        listaAdministradoresSistema = new ArrayList<>();
        listaVendedoresSistema = new ArrayList<>();
        listaProductosSistemaVendidos = new ArrayList<>();
        listaProductosSistemaCancelados = new ArrayList<>();
        listaProductosSistemaPublicados = new ArrayList<>();
        listaPersonaEnElSistema = new ArrayList<>();
        generarCopia();
        cargarDatos();
    }





    public void  generarCopia() {
        try {
          Persistencia.generarCopia();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void guardarDatos() throws Exception {
        try {
            // Guardar listas en Persistencia
            Persistencia.guardarListaAdministradoresSistemaXML(listaAdministradoresSistema);
            Persistencia.guardarListaVendedoresSistemaXML(listaVendedoresSistema);
            Persistencia.guardarListaProductosVendidosXML(listaProductosSistemaVendidos);
            Persistencia.guardarListaProductosCanceladosXML(listaProductosSistemaCancelados);
            Persistencia.guardarListaProductosPublicadosXML(listaProductosSistemaPublicados);
            Persistencia.guardarListaPersonasBin(listaPersonaEnElSistema);

        } catch (IOException e) {
            Persistencia.guardaRegistroLog("Error al guardar los datos del sistema",3,e.getMessage());

        }
    }




    public void cargarDatos()  {
        try {
            // Cargar listas desde Persistencia
            ArrayList<Administrador> administradores = Persistencia.cargarListaAdministradoresSistemaXML();
            ArrayList<Vendedor> vendedores = Persistencia.cargarListaVendedoresSistemaXML();
            ArrayList<Producto> productosVendidos = Persistencia.cargarListaProductosVendidosXML();
            ArrayList<Producto> productosCancelados = Persistencia.cargarListaProductosCanceladosXML();
            ArrayList<Producto> productosPublicados = Persistencia.cargarListaProductosPublicadosXML();
            ArrayList<Persona> personas = Persistencia.cargarListaPersonasBin();

            // Verificar y agregar cada lista a la lista correspondiente en el sistema
            if (administradores != null) {
                listaAdministradoresSistema.addAll(administradores);
            }
            if (vendedores != null) {
                listaVendedoresSistema.addAll(vendedores);
            }
            if (productosVendidos != null) {
                listaProductosSistemaVendidos.addAll(productosVendidos);
            }
            if (productosCancelados != null) {
                listaProductosSistemaCancelados.addAll(productosCancelados);
            }
            if (productosPublicados != null) {
                listaProductosSistemaPublicados.addAll(productosPublicados);
            }
            if (personas != null) {
                listaPersonaEnElSistema.addAll(personas);
            }

        } catch (Exception e) {
                Persistencia.guardaRegistroLog("Error al cargar la persistencia: " + e.getMessage(), 3, "Carga de archivos serializados");
        }
    }


    public Administrador crearAdministrador(String nombre,
                                      String cedula, String apellido,String correo, String contrasenia) throws Exception {
        if(!(nombre.isEmpty()|| cedula.isEmpty()||apellido.isEmpty()|| correo.isEmpty()||contrasenia.isEmpty())){


            Administrador administrador = new Administrador.Builder()
                    .setNombre(nombre)
                    .setCedula(cedula)
                    .setApellido(apellido)
                    .setCorreo(correo)
                    .setContrasenia(contrasenia).build();
            Persistencia.guardarObjetoTxt(administrador);
            listaAdministradoresSistema.add(administrador);
            crearPersona(nombre, cedula, apellido);
            guardarDatos();
            return administrador;


        }else{
            throw new Exception("NO has ingresado todos los datos bien");
        }

    }

    public Vendedor crearVendedor(String nombre,
                                            String cedula, String apellido,String correo, String contrasenia) throws Exception {
        if(!(nombre.isEmpty()|| cedula.isEmpty()||apellido.isEmpty()|| correo.isEmpty()||contrasenia.isEmpty())){

            Vendedor vendedor =  new Vendedor.Builder()
                    .setNombre(nombre)
                    .setCedula(cedula)
                    .setApellido(apellido)
                    .setCorreo(correo)
                    .setContrasenia(contrasenia)
                    .setProductos(new ArrayList<>())
                    .setSolicitudes(new ArrayList<>())
                    .build();

            Persistencia.guardarObjetoTxt(vendedor);
            listaVendedoresSistema.add(vendedor);
            crearPersona(nombre, cedula, apellido);
            guardarDatos();
            return vendedor;


        }else{
            throw new Exception("NO has ingresado todos los datos bien");
        }

    }


    public void crearPersona(String nombre, String cedula, String apellido)  {

        if(!(listaPersonaEnElSistema.isEmpty())){
            for(Persona persona: new ArrayList<>(listaPersonaEnElSistema)) {
                try {
                    if(persona.getNombre().equals(nombre) && persona.getApellido().equals(apellido) && persona.getCedula().equals(cedula)) {
                        Persistencia.guardaRegistroLog("La persona " + persona.getNombre() + " ha creado una nueva cuenta", 1, "Creacion de nueva cuenta");
                    }else if (!(persona.getNombre().equals(nombre)) || !(persona.getApellido().equals(apellido)) && persona.getCedula().equals(cedula)) {
                        Persistencia.guardaRegistroLog("La persona con cedula" + cedula + "Ha creado una cuenta con diferente nombre y apellido", 1, "Creacion de cuenta");
                        Persona person = new Persona.Builder().setApellido(apellido).setCedula(cedula).setNombre(nombre).build();
                        listaPersonaEnElSistema.add(person);
                        Persistencia.guardarObjetoTxt(person);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }else{
            try {
                Persistencia.guardaRegistroLog("La persona " + nombre + " ha creado una cuenta", 1, "Persona nueva en el sistema");
                Persona person = new Persona.Builder().setApellido(apellido).setCedula(cedula).setNombre(nombre).build();
                listaPersonaEnElSistema.add(person);
                Persistencia.guardarObjetoTxt(person);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }
    //falta
    public void comprarProducto(Producto producto, int cantidad) throws Exception {

        String identificador = producto.getCodigo();

        for(Vendedor vendedor: listaVendedoresSistema) {
            for(Producto p: vendedor.getProductos()){
                if(p.getCodigo().equals(identificador)){
                    int total = p.getCantidad() - cantidad;
                    if (total > 0) {
                        p.setCantidad(total);
                        listaProductosSistemaVendidos.add(p);
                    }else if(total == 0){
                        p.setCantidad(0);
                        listaProductosSistemaVendidos.add(p);
                        listaProductosSistemaPublicados.remove(p);
                    }else{
                        Persistencia.guardaRegistroLog("Se ha intentando vender un producto fuera de stock",1,"Recomendacion");

                        throw new ProductoFueraDeStock("El producto que ha intentado commprar esta fuera de stock");
                    }
                }
            }
        }
        guardarDatos();


    }

    public Persona buscarPersona(String identificador, String nombre, String apellido) {

        for(Persona persona: listaPersonaEnElSistema) {
            if(persona.getNombre().equals(nombre) && persona.getApellido().equals(apellido) && persona.getCedula().equals(identificador)) {
                return persona;
            }
        }
        return null;
    }




}
