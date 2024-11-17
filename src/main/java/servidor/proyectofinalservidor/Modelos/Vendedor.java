package servidor.proyectofinalservidor.Modelos;



import servidor.proyectofinalservidor.Excepciones.MaximoContactosException;
import servidor.proyectofinalservidor.Excepciones.ProductoDuplicadoException;
import servidor.proyectofinalservidor.Excepciones.ProductoNoDisponibleException;
import servidor.proyectofinalservidor.Excepciones.VendedorNoEncontradoException;
import servidor.proyectofinalservidor.Modelos.Enum.EstadoProducto;

import java.io.Serializable;
import java.util.ArrayList;

public class Vendedor extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String contrasenia;
    private String direccion;
    private String correo;
    private ArrayList<Producto> productos;
    private ArrayList<Vendedor> contactos;
    private ArrayList<Vendedor> solicitudes;
    private ArrayList<Producto> productosComprados;
    private int reputacionVendedor;
    private ArrayList<Reputacion> calificaciones;
    private ArrayList<Chat> chats ;

    // Constructor vacío
    public Vendedor() {
        super();
        this.chats = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.contactos = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.productosComprados = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
    }

    // Constructor con parámetros
    public Vendedor(String nombre, String apellido, String cedula, String contrasenia, String direccion,
                    String correo, ArrayList<Producto> productos, ArrayList<Vendedor> contactos,
                    ArrayList<Vendedor> solicitudes, ArrayList<Producto> productosComprados, int reputacionVendedor, ArrayList<Reputacion> calificaciones, ArrayList<Chat> chats) {
        super(nombre, cedula, apellido); // Llamar al constructor de Persona
        this.contrasenia = contrasenia;
        this.chats = chats;
        this.reputacionVendedor = reputacionVendedor;
        this.direccion = direccion;
        this.correo = correo;
        this.productos = productos;
        this.contactos = contactos;
        this.solicitudes = solicitudes;
        this.productosComprados = productosComprados;
        this.calificaciones = calificaciones;
    }



    // Getters
    public String getContrasenia() {
        return contrasenia;
    }
    public ArrayList<Producto> getProductosComprados(){
        return productosComprados;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Vendedor> getContactos() {
        return contactos;
    }
    public ArrayList<Vendedor> getSolicitudes() {
        return solicitudes;
    }
    public int getReputacionVendedor() {
        return reputacionVendedor;
    }

    public ArrayList<Reputacion> getCalificaciones() {
        return calificaciones;
    }



    public ArrayList<Chat> getChats() {
        return chats;
    }


    // Setters
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public void setCalificaciones(ArrayList<Reputacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public void setReputacionVendedor(int reputacionVendedor) {
        this.reputacionVendedor = reputacionVendedor;
    }

    public void setProductosComprados(ArrayList<Producto> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void setContactos(ArrayList<Vendedor> contactos) {
        this.contactos = contactos;
    }
    public void setSolicitudes(ArrayList<Vendedor> solicitudes) {
        this.solicitudes = solicitudes;
    }



    // toString method
    @Override
    public String toString() {
        return "Vendedor{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", cedula='" + getCedula() + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                ", productos=" + productos +  '\'' +
                ", contactos=" + contactos +'\'' +
                ", calificaciones=" + calificaciones +'\'' +
                ", Solicitudes=" + solicitudes +
                '}';
    }

    // Builder pattern
    public static class Builder extends Persona.Builder {
        private String contrasenia;
        private String direccion;
        private String correo;
        private ArrayList<Producto> productos = new ArrayList<>();
        private ArrayList<Vendedor> contactos = new ArrayList<>();
        private ArrayList<Vendedor> solicitudes = new ArrayList<>();
        private int reputacionVendedor;
        private ArrayList<Producto> productosComprados = new ArrayList<>();
        private ArrayList<Reputacion> calificaciones = new ArrayList<>();
        private ArrayList<Chat> chats = new ArrayList<>();


        public Builder() {
            super();
        }

        // Métodos heredados de Persona para Nombre, Apellido y Cedula
        @Override
        public Builder setNombre(String nombre) {
            super.setNombre(nombre);
            return this;
        }

        @Override
        public Builder setApellido(String apellido) {
            super.setApellido(apellido);
            return this;
        }

        @Override
        public Builder setCedula(String cedula) {
            super.setCedula(cedula);
            return this;
        }


        // Métodos específicos de Vendedor
        public Builder setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
            return this;
        }
        public Builder setCalificaciones(ArrayList<Reputacion> calificaciones) {
            this.calificaciones = calificaciones;
            return this;
        }
        public Builder setProductosComprados(ArrayList<Producto> productosComprados) {
            this.productosComprados = productosComprados;
            return this;
        }

        public Builder setReputacionVendedor(int reputacionVendedor) {
            this.reputacionVendedor = reputacionVendedor;
            return this;
        }

        public Builder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }
        public Builder setSolicitudes(ArrayList<Vendedor> solicitudes) {
            this.solicitudes = solicitudes;
            return this;
        }

        public Builder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder setProductos(ArrayList<Producto> productos) {
            this.productos = productos;
            return this;
        }

        public Builder setContactos(ArrayList<Vendedor> contactos) {
            this.contactos = contactos;
            return this;
        }
        public Builder setChats(ArrayList<Chat> chats) {
            this.chats = chats;
            return this;
        }

        // Método build para crear un Vendedor
        public Vendedor build() {
            return new Vendedor(nombre, apellido, cedula, contrasenia, direccion, correo, productos, contactos,solicitudes,productosComprados,reputacionVendedor, calificaciones, chats);
        }
    }

    // Excepcion 1
    public Producto buscarVendedorPorNombre(String nombre) throws VendedorNoEncontradoException {

        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        // Si no se encuentra el producto, lanzamos la excepción
        throw new VendedorNoEncontradoException("Producto con nombre " + nombre + " no encontrado.");
    }

    //Excepcion 2

    public Producto agregarProducto(Producto producto) throws ProductoDuplicadoException {
        for (Producto p : productos) {
            if (p.getNombre().equals(producto.getNombre())) {
                throw new ProductoDuplicadoException("El producto con nombre " + producto.getNombre() + " ya existe.");
            }
        }
        productos.add(producto);
        System.out.println("Producto agregado exitosamente.");
        return producto;  // Retornar el producto que se acaba de agregar
    }

    //Excepcion 3

    public void agregarContacto(Vendedor contacto) throws MaximoContactosException {
        if (contactos.size() >= 10) {
            throw new MaximoContactosException("No puedes agregar más de 10 contactos.");
        }
        contactos.add(contacto);
        System.out.println("Contacto agregado exitosamente.");
    }

    //Excepcion 4
    public void venderProducto(Producto producto) throws ProductoNoDisponibleException {
        if (producto.getEstado() != EstadoProducto.PUBLICADO) {
            throw new ProductoNoDisponibleException("El producto " + producto.getNombre() + " no está disponible para la venta.");
        }
        //producto.vender();
        System.out.println("Producto vendido exitosamente.");
    }
}
