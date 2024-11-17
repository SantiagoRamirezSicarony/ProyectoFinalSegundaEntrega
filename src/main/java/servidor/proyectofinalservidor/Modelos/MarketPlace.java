package servidor.proyectofinalservidor.Modelos;

import servidor.proyectofinalservidor.Excepciones.*;
import servidor.proyectofinalservidor.Modelos.Enum.EstadoProducto;
import servidor.proyectofinalservidor.Servicios.*;
import servidor.proyectofinalservidor.Utilidades.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class MarketPlace implements EstadisticasService,AdministradService, AutenticacionService,ProductoService, ReputacionService, VendedorService, ComentarioService {
    private ArrayList<Administrador> listaAdministradoresSistema;
    private ArrayList<Vendedor> listaVendedoresSistema;
    private ArrayList<Producto> listaProductosSistemaVendidos;
    private ArrayList<Producto> listaProductosSistemaCancelados;
    private ArrayList<Producto> listaProductosSistemaPublicados;
    private ArrayList<Persona> listaPersonaEnElSistema;
    private ArrayList<Producto> todosLosProductos;




    public MarketPlace() {
        listaAdministradoresSistema = new ArrayList<>();
        listaVendedoresSistema = new ArrayList<>();
        listaProductosSistemaVendidos = new ArrayList<>();
        listaProductosSistemaCancelados = new ArrayList<>();
        listaProductosSistemaPublicados = new ArrayList<>();
        listaPersonaEnElSistema = new ArrayList<>();
        todosLosProductos =  new ArrayList<>();
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
            Persistencia.guardarListaProductosXML(todosLosProductos);

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
            ArrayList<Producto> todosLosProductosCargados = Persistencia.cargarTodosLosProductos();
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
            if (todosLosProductosCargados != null){
                todosLosProductos.addAll(todosLosProductosCargados);
            }

        } catch (Exception e) {
                Persistencia.guardaRegistroLog("Error al cargar la persistencia: " + e.getMessage(), 3, "Carga de archivos serializados");
        }
    }

    @Override
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
    @Override
    public Vendedor crearVendedor(String nombre,
                                            String cedula, String apellido,String correo, String contrasenia, String direccion) throws Exception {
        if(!(nombre.isEmpty()|| cedula.isEmpty()||apellido.isEmpty()|| correo.isEmpty()||contrasenia.isEmpty()||direccion.isEmpty())){

            for(Vendedor v: listaVendedoresSistema){
                if(!(v.getCorreo().equals(correo))){
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
                            .setDireccion(direccion)
                            .setChats(new ArrayList<>())
                            .build();

                    Persistencia.guardarObjetoTxt(vendedor);
                    listaVendedoresSistema.add(vendedor);
                    crearPersona(nombre, cedula, apellido);
                    guardarDatos();
                    return vendedor;

                }else{
                    Persistencia.guardaRegistroLog("Ya esta en uso el correo por otro usuario", 1, "Ingreso de datos");
                    throw new Exception("Correo ya en uso");
                }
            }



        }else{
            Persistencia.guardaRegistroLog("No se han ingresado todo los datos bien", 1, "Ingreso de datos");
            throw new Exception("NO has ingresado todos los datos bien");
        }

        return null;
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
                    contacto.getContactos().add(inicial);
                    ArrayList<Persona> participantes = new ArrayList<>();
                    participantes.add(inicial);
                    participantes.add(contacto);
                    Chat chat = new Chat(new ArrayList<>(), participantes);
                    inicial.getChats().add(chat);
                    contacto.getChats().add(chat);

                }
            }
        }else{
            throw new ArgumentosFaltantesException("Falta el vendedor y el contacto");
        }

    }
    @Override
    public void ennviarSolicitud(Vendedor solicitador, Vendedor enviarSolicitud) throws ArgumentosFaltantesException {
        if(solicitador != null && enviarSolicitud != null){

           enviarSolicitud.getSolicitudes().add(solicitador);

        }else{
            Persistencia.guardaRegistroLog("Se ha intentado enviar una Solictud y no se selecciono el vendedor",2,"Intento de enviar una Solicitud");
            throw new ArgumentosFaltantesException("Falta seleccionar el vendedor que quieres añadir");
        }

    }
    @Override
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
    //falta
    @Override
    public ArrayList<Vendedor> buscarVendedor(String cedula, String nombre, String apellido)throws ArgumentosFaltantesException{
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        if(cedula != null && nombre != null && apellido != null){
            for(Vendedor v : listaVendedoresSistema){
                if(v.getNombre().equals(nombre) || v.getApellido().equals(apellido) || v.getCedula().equals(cedula)){
                    vendedores.add(v);
                }
            }
        }else{
            Persistencia.guardaRegistroLog("Se intento buscar una lista de vendedores pero no llegaron todos los parametros",1,"Falta de informacion");
        }

        return vendedores;
    }


    @Override
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
    @Override
    public void comprarProducto(String codigo, int cantidad) throws Exception {
        Producto producto = buscarProductoExacto(codigo);
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
    @Override
    public ArrayList<Producto> buscarProducto(String codigo, String nombre) throws ProductoNoDisponibleException {
        ArrayList<Producto> productos = new ArrayList<>();
        if(codigo != null){
            for(Vendedor vendedor: listaVendedoresSistema){
                for(Producto p: vendedor.getProductos()){
                    if(p.getCodigo().equals(codigo) || p.getNombre().equals(nombre)){
                        productos.add(p);
                    }
                }

            }
        }else{
            throw new ProductoNoDisponibleException("No se han encontrado el producto con ese codigo ni con el nombre");
        }
        return productos;


    }


//Ejemplo de como se guarda un registro log
    @Override
    public Producto crearProducto(String nombre, String codigo, byte[] imagen, String categoria, double precio,
                                  String descripcion, int cantidad, Vendedor vendedor){
        if (nombre != null && !nombre.isEmpty() &&
                codigo != null && !codigo.isEmpty() &&
                imagen != null &&
                categoria != null && !categoria.isEmpty() &&
                precio > 0 &&
                descripcion != null && !descripcion.isEmpty() &&
                cantidad > 0) {

            Producto producto = new Producto.Builder()
                    .setCategoria(categoria)
                    .setCodigo(generarCodigoProducto())
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
            Persistencia.guardaRegistroLog("Se ha creado un producto",1,"producto nuevo");


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


    //Falta
    @Override
    public boolean modificarProducto(String nombre, byte[] imagen, String categoria, double precio, String descripcion, EstadoProducto estado, String codigo, int cantidad) throws ProductoNoDisponibleException {
        try {
            Producto producto = buscarProductoExacto(codigo);
            if (producto == null) {
                throw new IllegalArgumentException("El producto no puede ser nulo.");
            }

            boolean modificado = false;
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
        } catch (ProductoNoDisponibleException e) {
            Persistencia.guardaRegistroLog("Producto no disponible al modificar: " + e.getMessage(), 2, "Modificar producto");
            throw e;
        } catch (IllegalArgumentException e) {
            Persistencia.guardaRegistroLog("Argumento inválido: " + e.getMessage(), 1, "Modificar producto");
            throw e;
        } catch (Exception e) {
            Persistencia.guardaRegistroLog("Error inesperado al modificar producto: " + e.getMessage(), 3, "Modificar producto");
            throw e;
        }
    }

    @Override
    public Producto buscarProductoExacto(String codigo) throws ProductoNoDisponibleException {
        if(codigo != null){
            for(Vendedor vendedor: listaVendedoresSistema){
                for(Producto p: vendedor.getProductos()){
                    if(p.getCodigo().equals(codigo)){
                        return p;
                    }
                }

            }
        }else{
            throw new ProductoNoDisponibleException("No se han encontrado el producto con ese codigo ni con el nombre");
        }
        return null;

    }

    //Falta
    @Override
    public void eliminarProducto(String codigo, Vendedor vendedor) {
        try {
            Producto producto = buscarProductoExacto(codigo);
            vendedor.getProductos().removeIf(p -> p.getCodigo().equals(codigo));
            listaProductosSistemaPublicados.removeIf(p -> p.getCodigo().equals(codigo));
            listaProductosSistemaCancelados.removeIf(p -> p.getCodigo().equals(codigo));
        } catch (ProductoNoDisponibleException e) {
            Persistencia.guardaRegistroLog("Producto no disponible al intentar eliminar: " + e.getMessage(), 2, "Eliminar producto");
        } catch (Exception e) {
            Persistencia.guardaRegistroLog("Error inesperado al eliminar producto: " + e.getMessage(), 3, "Eliminar producto");
        }
    }
    //Falta
    @Override
    public void calificarVendedor(String comentario, int calificacion, Vendedor calificado, Vendedor calificador) {
        try {
            // Aquí iría la lógica para calificar al vendedor
        } catch (Exception e) {
            Persistencia.guardaRegistroLog("Error al calificar vendedor: " + e.getMessage(), 3, "Calificar vendedor");
        }
    }

    @Override
    public Comentario crearComentario(String mensaje, Date fecha, Persona autor, Producto producto) throws ArgumentosFaltantesException {
        try {
            if (mensaje != null && !mensaje.isEmpty() && fecha != null && autor != null && producto != null) {
                return new Comentario(mensaje, fecha, autor, producto);
            } else {
                throw new ArgumentosFaltantesException("Faltan argumentos para crear el comentario");
            }
        } catch (ArgumentosFaltantesException e) {
            Persistencia.guardaRegistroLog("Error al crear comentario: " + e.getMessage(), 2, "Crear comentario");
            throw e;
        } catch (Exception e) {
            Persistencia.guardaRegistroLog("Error inesperado al crear comentario: " + e.getMessage(), 3, "Crear comentario");
            throw e;
        }
    }

    @Override
    public EstadisticaProducto generarEstadisticaProducto(double calificacionProducto, int meGustas) {

        return null;
    }

    @Override
    public EstadisticaVendedor generarEstadisticaVendedor(int productosVendidos, int productosPublicados, int mensajesEnviados) throws EstadisticaNoDisponibleException {

            //if (this.productosVendidos == 0 && this.mensajesEnviados == 0) {
              //  throw new EstadisticaNoDisponibleException("No hay estadísticas disponibles para este vendedor.");
            //}
            //System.out.println("Estadísticas disponibles.");
        return null;
    }


    /**
     * Método que crea un número de cuenta aleatorio y verifica que no exista en el sistema para evitar duplicados
     * @return número de cuenta
     */
    @Override
    public String generarCodigoProducto(){

        String codigoFactura = crearNumeroCodigoProducto();

        while(validarCodigoProducto(codigoFactura) != null){
            codigoFactura = crearNumeroCodigoProducto();
        }

        return codigoFactura;
    }

    /**
     * Método que genera un número de cuenta aleatorio de 10 dígitos
     * @return número de cuenta
     */
    @Override
    public String crearNumeroCodigoProducto(){
        StringBuilder numeroCuenta = new StringBuilder();

        for(int i = 0; i < 10; i++){
            int numero = new Random().nextInt(10);
            numeroCuenta.append(numero);
        }

        return numeroCuenta.toString();
    }

    /**
     * Método que valida si un codigo de factura ya existe
     * @param codigo número de cuenta
     * @return cuenta de ahorros o null si no existe
     */

    @Override
    public String validarCodigoProducto(String codigo){
        for(int i = 0; i < todosLosProductos.size(); i++){
            if(todosLosProductos.get(i).getCodigo().equals(codigo)){
                System.out.println("se encontro el codigo" + todosLosProductos.get(i).getCodigo());
                return todosLosProductos.get(i).getCodigo();
            }
        }
        return null;
    }


    @Override
    public boolean autenticar(String correo, String contrasenia) {
        if(correo != null && contrasenia != null){
            for(Vendedor vendedor: listaVendedoresSistema){
                if(correo.equals(vendedor.getCorreo())&&contrasenia.equals(vendedor.getContrasenia())){
                    return true;
                }

            }
        }else{
            Persistencia.guardaRegistroLog("Intento de ingreso al sistema fallido",1,"Ingreso al sistema");
            throw new IllegalArgumentException("Ha intentado ingresar con unos datos incorrectos");
        }
        return false;
    }
    @Override
    public ArrayList<Vendedor> sugerirAmistades(Vendedor vendedorActual) {
        ArrayList<Vendedor> posiblesSugerencias = new ArrayList<>();

        for (Vendedor vendedor : listaVendedoresSistema) {
            // Evitar sugerir al mismo vendedor y a los contactos existentes
            if (!vendedor.equals(vendedorActual) &&
                    !vendedorActual.getContactos().contains(vendedor) &&
                    !vendedorActual.getSolicitudes().contains(vendedor)) {
                posiblesSugerencias.add(vendedor);
            }
        }

        // Mezclar la lista para obtener vendedores al azar
        Collections.shuffle(posiblesSugerencias);

        // Retornar un máximo de 10 sugerencias
        return new ArrayList<>(posiblesSugerencias.subList(0, Math.min(10, posiblesSugerencias.size())));
    }

    @Override
    public Vendedor buscarVendedorExacto(String correo) throws ArgumentosFaltantesException{
        for(Vendedor vendedor: listaVendedoresSistema){
            if(correo.equals(vendedor.getCorreo())){
                return vendedor;
            }
        }
        return null;
    }
}
