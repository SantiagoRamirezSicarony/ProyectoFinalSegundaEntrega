package servidor.proyectofinalservidor.Servicios;

import servidor.proyectofinalservidor.Modelos.Vendedor;

public interface ReputacionService {
    void calificarVendedor(String comentario, int calificacion, Vendedor calificado, Vendedor calificador);
}
