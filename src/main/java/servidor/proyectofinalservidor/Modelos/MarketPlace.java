package servidor.proyectofinalservidor.Modelos;

import servidor.proyectofinalservidor.Excepciones.ArgumentosFaltantesException;
import servidor.proyectofinalservidor.Excepciones.ContactoEncontradoException;
import servidor.proyectofinalservidor.Excepciones.MaximoContactosException;
import servidor.proyectofinalservidor.Excepciones.ProductoFueraDeStock;
import servidor.proyectofinalservidor.Servicios.EstadisticasService;
import servidor.proyectofinalservidor.Servicios.ProductoService;
import servidor.proyectofinalservidor.Servicios.ReputacionService;
import servidor.proyectofinalservidor.Servicios.VendedorService;
import servidor.proyectofinalservidor.Utilidades.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarketPlace implements EstadisticasService, ProductoService, ReputacionService, VendedorService {
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
            Persistencia.guardaRegistroLog("No se han ingresado todo los datos bien", 1, "Ingreso de datos");

            throw new Exception("NO has ingresado todos los datos bien");
        }

    }

    public Vendedor crearVendedor(String nombre,
                                            String cedula, String apellido,String correo, String contrasenia, String direccion) throws Exception {
        if(!(nombre.isEmpty()|| cedula.isEmpty()||apellido.isEmpty()|| correo.isEmpty()||contrasenia.isEmpty()||direccion.isEmpty())){

            Vendedor vendedor =  new Vendedor.Builder()
                    .setNombre(nombre)
                    .setCedula(cedula)
                    .setApellido(apellido)
                    .setCorreo(correo)
                    .setContrasenia(contrasenia)
                    .setProductos(new ArrayList<>())
                    .setSolicitudes(new ArrayList<>())
                    .setCalificaciones(new ArrayList<>())
                    .setProductosComprados(new ArrayList<>())
                    .setContactos(new ArrayList<>())
                    .setReputacionVendedor(0)
                    .build();

            Persistencia.guardarObjetoTxt(vendedor);
            listaVendedoresSistema.add(vendedor);
            crearPersona(nombre, cedula, apellido);
            guardarDatos();
            return vendedor;


        }else{
            Persistencia.guardaRegistroLog("No se han ingresado todo los datos bien", 1, "Ingreso de datos");
            throw new Exception("NO has ingresado todos los datos bien");
        }

    }

    @Override
    public void agregarContacto(Vendedor inicial, Vendedor contacto) throws MaximoContactosException, ContactoEncontradoException, ArgumentosFaltantesException {

        if(inicial != null && contacto != null){
            if(inicial.getContactos().size() == 10 ){
                throw new MaximoContactosException("El vendededor: " + inicial.getNombre() + " ya tiene 10 contactos, por lo tanto no puede registar mas");
            }else if(inicial.getContactos().size() < 10){

                if(encontrarContactos(inicial,contacto)){
                    throw new ContactoEncontradoException("Ya tienes esta persona en tus contactos");
                }else{
                    inicial.getContactos().add(contacto);
                }
            }
        }else{
            throw new ArgumentosFaltantesException("Falta el vendedor y el contacto");
        }

    }

    public boolean encontrarContactos(Vendedor inicial, Vendedor contacto){
        if(inicial != null && contacto != null){
            for(Vendedor v : inicial.getContactos()){
                if(v.getCedula().equals(contacto.getCedula()) && v.getNombre().equals(contacto.getNombre()) && v.getApellido().equals(contacto.getApellido())){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void comentarProducto(String mensaje, Date fecha, Persona autor, Producto producto) {


    }

    @Override
    public void darMeGusta(Date fecha, Persona autor, Producto producto) {

    }

    @Override
    public Vendedor buscarVendedor(String cedula) {
        return null;
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

    public Producto crearProducto(String nombre, String codigo, String imagen, String categoria, double precio,
                                  String descripcion, int cantidad, Vendedor vendedor){
        if (nombre != null && !nombre.isEmpty() &&
                codigo != null && !codigo.isEmpty() &&
                imagen != null && !imagen.isEmpty() &&
                categoria != null && !categoria.isEmpty() &&
                precio > 0 &&
                descripcion != null && !descripcion.isEmpty() &&
                cantidad > 0) {

            Producto producto = new Producto.Builder()
                    .setCategoria(categoria)
                    .setCodigo(codigo)
                    .setCantidad(cantidad)
                    .setComentarios(new ArrayList<>())
                    .setDescripcion(descripcion)
                    .setEstado(EstadoProducto.PUBLICADO)
                    .setImagen(imagen)
                    .setNombre(nombre)
                    .setMeGustas(new ArrayList<>())
                    .setPrecio(precio)
                    .build();
            vendedor.getProductos().add(producto);
            listaProductosSistemaPublicados.add(producto);

            try {
                guardarDatos();
            } catch (Exception e) {
                Persistencia.guardaRegistroLog("Hubo un error en el metodo de crear producto, al momento de guardar los datos",3,"Guardar Informacion");
                throw new RuntimeException(e);
            }
            return producto;


            // Todos los campos son válidos, puedes continuar con el resto del código

        } else {
            // Al menos un campo no es válido
            Persistencia.guardaRegistroLog("No llegan todos  los datos necesarios para crear un producto",1,"Crear un producto");
            return null;

        }




    }


    @Override
    public Producto crearProducto(String nombre, String codigo, String imagen, String categoria, double precio, String descripcion, EstadoProducto estado, List<Comentario> comentarios, List<MeGusta> meGustas, int cantidad, Date fechaPublicacion) {
        return null;
    }

    @Override
    public void modificarProducto(String nombre, String codigo, String imagen, String categoria, double precio, String descripcion, EstadoProducto estado, List<Comentario> comentarios, List<MeGusta> meGustas, int cantidad, Date fechaPublicacion, Producto poducto) {

    }

    @Override
    public void eliminarProducto(Producto producto, Vendedor vendedor) {

    }

    @Override
    public void calificarVendedor(String comentario, int calificacion, Vendedor calificado, Vendedor calificador) {

    }
}
