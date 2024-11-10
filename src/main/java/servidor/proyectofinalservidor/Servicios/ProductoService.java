package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Modelos.*;

import java.util.Date;
import java.util.List;

public interface ProductoService {
    public Producto crearProducto(String nombre, String codigo, String imagen, String categoria, double precio,
                                  String descripcion, EstadoProducto estado, List<Comentario> comentarios,
                                  List<MeGusta> meGustas, int cantidad, Date fechaPublicacion);
    public void modificarProducto(String nombre, String codigo, String imagen, String categoria, double precio,
                                  String descripcion, EstadoProducto estado, List<Comentario> comentarios,
                                  List<MeGusta> meGustas, int cantidad, Date fechaPublicacion, Producto poducto);
    public void eliminarProducto(Producto producto, Vendedor vendedor);

}
