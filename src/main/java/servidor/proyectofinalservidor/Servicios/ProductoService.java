package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Excepciones.ProductoNoDisponibleException;
import servidor.proyectofinalservidor.Modelos.*;
import servidor.proyectofinalservidor.Modelos.Enum.EstadoProducto;

import java.util.ArrayList;

public interface ProductoService {
    Producto crearProducto(String nombre, String codigo, byte[] imagen, String categoria, double precio,
                                  String descripcion, int cantidad, Vendedor vendedor);
    boolean modificarProducto(String nombre, byte[] imagen, String categoria, double precio, String descripcion, EstadoProducto estado, String  codigo, int cantidad) throws ProductoNoDisponibleException;
    void eliminarProducto(String codigo, Vendedor vendedor);
    String validarCodigoProducto(String codigo);
    String crearNumeroCodigoProducto();
    String generarCodigoProducto();
    ArrayList<Producto> buscarProducto(String codigo, String nombre) throws ProductoNoDisponibleException;
    Producto buscarProductoExacto(String codigo) throws ProductoNoDisponibleException;
    void comprarProducto(String codigo, int cantidad) throws Exception;
}
