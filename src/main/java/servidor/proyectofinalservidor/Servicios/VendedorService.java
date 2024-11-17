package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Excepciones.ArgumentosFaltantesException;
import servidor.proyectofinalservidor.Excepciones.ContactoEncontradoException;
import servidor.proyectofinalservidor.Excepciones.MaximoContactosException;
import servidor.proyectofinalservidor.Excepciones.ProductoNoDisponibleException;
import servidor.proyectofinalservidor.Modelos.Persona;
import servidor.proyectofinalservidor.Modelos.Producto;
import servidor.proyectofinalservidor.Modelos.Vendedor;

import java.util.ArrayList;
import java.util.Date;

public interface VendedorService {
    Vendedor crearVendedor(String nombre,String cedula, String apellido,String correo, String contrasenia,String direccion) throws Exception;
    void agregarContacto(Vendedor inicial, Vendedor contacto) throws MaximoContactosException, ContactoEncontradoException, ArgumentosFaltantesException;
    void comentarProducto(String mensaje, Date fecha, Persona autor, Producto producto) throws ArgumentosFaltantesException, ProductoNoDisponibleException;
    void darMeGusta(Date fecha, Vendedor autor, Producto producto) throws ArgumentosFaltantesException;
    ArrayList<Vendedor> buscarVendedor(String cedula, String nombre, String apellido)throws ArgumentosFaltantesException;
    boolean encontrarContactos(Vendedor inicial, Vendedor contacto);
    void ennviarSolicitud(Vendedor solicitador, Vendedor enviarSolicitud) throws ArgumentosFaltantesException;
    ArrayList<Vendedor> sugerirAmistades(Vendedor vendedorActual);
    Persona buscarVendedorExacto(String correo) throws ArgumentosFaltantesException;
}
