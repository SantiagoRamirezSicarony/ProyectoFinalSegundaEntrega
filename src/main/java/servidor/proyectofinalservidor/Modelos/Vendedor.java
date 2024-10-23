package servidor.proyectofinalservidor.Modelos;

import co.edu.uniquindio.poo.Excepciones.MaximoContactosException;
import co.edu.uniquindio.poo.Excepciones.ProductoDuplicadoException;
import co.edu.uniquindio.poo.Excepciones.ProductoNoDisponibleException;
import co.edu.uniquindio.poo.Excepciones.VendedorNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Persona {
    private static final long serialVersionUID = 1L;

    private String contraseña;
    private String direccion;

    private List<Producto> productos;
    private List<Vendedor> contactos;

    // Constructor con builder
    private Vendedor(VendedorBuilder builder) {
        super(builder.nombre, builder.cedula, builder.password);
        this.apellido = builder.apellido;
        this.direccion = builder.direccion;
        this.productos = builder.productos;
        this.contactos = builder.contactos;
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
        producto.vender();
        System.out.println("Producto vendido exitosamente.");
    }

        
    // Builder
    public static class VendedorBuilder {
        private String nombre;
        private String cedula;
        private String password;
        private String apellido;
        private String direccion;
        private List<Producto> productos = new ArrayList<>();
        private List<Vendedor> contactos = new ArrayList<>();

        public VendedorBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public VendedorBuilder setCedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public VendedorBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public VendedorBuilder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public VendedorBuilder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public VendedorBuilder setProductos(List<Producto> productos) {
            this.productos = productos;
            return this;
        }

        public VendedorBuilder setContactos(List<Vendedor> contactos) {
            this.contactos = contactos;
            return this;
        }

        public Vendedor build() {
            return new Vendedor(this);
        }
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + getCedula() + '\'' +
                ", direccion='" + direccion + '\'' +
                ", productos=" + productos +
                ", contactos=" + contactos +
                '}';
    }
}
