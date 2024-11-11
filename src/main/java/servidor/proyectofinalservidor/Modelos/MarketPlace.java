package servidor.proyectofinalservidor.Modelos;

import servidor.proyectofinalservidor.Excepciones.*;
import servidor.proyectofinalservidor.Servicios.*;
import servidor.proyectofinalservidor.Utilidades.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MarketPlace implements EstadisticasService, ProductoService, ReputacionService, VendedorService, ComentarioService {
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
    public void comentarProducto(String mensaje, Date fecha, Persona autor, Producto producto) throws ArgumentosFaltantesException, ProductoNoDisponibleException {
        if( producto != null) {
            Comentario comentario = crearComentario(mensaje, fecha, autor, producto);
            producto.getComentarios().add(comentario);
        }else{
            throw new ProductoNoDisponibleException("Producto no existente");
        }
    }

    @Override
    public void darMeGusta(Date fecha, Vendedor autor, Producto producto) throws ArgumentosFaltantesException {
        if( fecha != null && autor != null && producto != null){
            MeGusta meGusta = new MeGusta(fecha,autor,producto);
            producto.getMeGustas().add(meGusta);
        }else{
            throw new ArgumentosFaltantesException("Faltan argumentos para dar un me gusta");
        }
    }

    @Override
    public ArrayList<Vendedor> buscarVendedor(String cedula, String nombre, String apellido)throws ArgumentosFaltantesException{
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
    public void comprarProducto(String codigo, int cantidad) throws Exception {
        Producto producto = buscarProducto(codigo);
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

    private Producto buscarProducto(String codigo) throws ProductoNoDisponibleException {
        if(codigo != null){
            for(Vendedor vendedor: listaVendedoresSistema){
                for(Producto p: vendedor.getProductos()){
                    if(p.getCodigo().equals(codigo)){
                        return p;
                    }
                }

            }
            throw new ProductoNoDisponibleException("No se ha encontrado el producto con ese codigo");
        }else{
            return null;
        }


    }

    public Persona buscarPersona(String identificador, String nombre, String apellido) {

        for(Persona persona: listaPersonaEnElSistema) {
            if(persona.getNombre().equals(nombre) && persona.getApellido().equals(apellido) && persona.getCedula().equals(identificador)) {
                return persona;
            }
        }
        return null;
    }

    @Override
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
    public boolean modificarProducto(String nombre, String imagen, String categoria, double precio, String descripcion, EstadoProducto estado, String  codigo, int cantidad) throws ProductoNoDisponibleException {

        Producto producto = buscarProducto(codigo);

        boolean modificado = false;

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (nombre != null && !producto.getNombre().equals(nombre)) {
            producto.setNombre(nombre);
            modificado = true;
        }


        if (imagen != null && !producto.getImagen().equals(imagen)) {
            producto.setImagen(imagen);
            modificado = true;
        }

        if (categoria != null && !producto.getCategoria().equals(categoria)) {
            producto.setCategoria(categoria);
            modificado = true;
        }

        if (precio >= 0 && producto.getPrecio() != precio) {
            producto.setPrecio(precio);
            modificado = true;
        } else if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (descripcion != null && !producto.getDescripcion().equals(descripcion)) {
            producto.setDescripcion(descripcion);
            modificado = true;
        }

        if (cantidad >= 0 && producto.getCantidad() != cantidad) {
            producto.setCantidad(cantidad);
            modificado = true;
        } else if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }

        return modificado;
    }


    @Override
    public void eliminarProducto(String codigo, Vendedor vendedor) {
        try{
            Producto producto = buscarProducto(codigo);
            vendedor.getProductos().removeIf(p -> p.getCodigo().equals(codigo));
            listaProductosSistemaPublicados.removeIf(p -> p.getCodigo().equals(codigo));
            listaProductosSistemaCancelados.removeIf(p -> p.getCodigo().equals(codigo));

        }catch (ProductoNoDisponibleException e){

        }

    }

    @Override
    public void calificarVendedor(String comentario, int calificacion, Vendedor calificado, Vendedor calificador) {

    }

    @Override
    public Comentario crearComentario(String mensaje, Date fecha, Persona autor, Producto producto) throws ArgumentosFaltantesException {
        if(mensaje != null && !mensaje.isEmpty() && !mensaje.equals("") && !mensaje.equals("null") && fecha != null && autor != null && producto != null){
            return new Comentario(mensaje, fecha, autor, producto);
        }else{
            throw new ArgumentosFaltantesException("Faltaron argumentos para generar el comentario");
        }

    }
}
