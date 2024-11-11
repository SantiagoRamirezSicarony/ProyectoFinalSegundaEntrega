package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Excepciones.ProductoNoDisponibleException;
import servidor.proyectofinalservidor.Modelos.*;

public interface ProductoService {
    public Producto crearProducto(String nombre, String codigo, String imagen, String categoria, double precio,
                                  String descripcion, int cantidad, Vendedor vendedor);
    public boolean modificarProducto(String nombre, String imagen, String categoria, double precio, String descripcion, EstadoProducto estado, String  codigo, int cantidad) throws ProductoNoDisponibleException;
    public void eliminarProducto(String codigo, Vendedor vendedor);

}
