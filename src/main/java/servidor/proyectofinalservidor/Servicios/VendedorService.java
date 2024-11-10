package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Excepciones.ArgumentosFaltantesException;
import servidor.proyectofinalservidor.Excepciones.ContactoEncontradoException;
import servidor.proyectofinalservidor.Excepciones.MaximoContactosException;
import servidor.proyectofinalservidor.Modelos.Persona;
import servidor.proyectofinalservidor.Modelos.Producto;
import servidor.proyectofinalservidor.Modelos.Vendedor;

import java.util.Date;

public interface VendedorService {
    public Vendedor crearVendedor(String nombre,String cedula, String apellido,String correo, String contrasenia,String direccion) throws Exception;
    public void agregarContacto(Vendedor inicial, Vendedor contacto) throws MaximoContactosException, ContactoEncontradoException, ArgumentosFaltantesException;
    public void comentarProducto(String mensaje, Date fecha, Persona autor, Producto producto);
    public void darMeGusta(Date fecha, Persona autor, Producto producto);
    public Vendedor buscarVendedor(String cedula);

}
