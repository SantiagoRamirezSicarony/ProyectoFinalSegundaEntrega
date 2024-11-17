package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Excepciones.EstadisticaNoDisponibleException;
import servidor.proyectofinalservidor.Modelos.EstadisticaProducto;
import servidor.proyectofinalservidor.Modelos.EstadisticaVendedor;

public interface EstadisticasService {
    EstadisticaProducto generarEstadisticaProducto(double calificacionProducto, int meGustas);
    EstadisticaVendedor generarEstadisticaVendedor(int productosVendidos,int productosPublicados,int mensajesEnviados) throws EstadisticaNoDisponibleException;
}