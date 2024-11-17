package servidor.proyectofinalservidor.App;

import servidor.proyectofinalservidor.Modelos.*;
import servidor.proyectofinalservidor.Server.Server;
import servidor.proyectofinalservidor.Utilidades.Persistencia;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
           // MarketPlace marketPlace = new MarketPlace();

            //    Vendedor vendedor = marketPlace.crearVendedor("Felipe",null,null,null,null);
       //Vendedor vendedor1 = marketPlace.crearVendedor("Felipe","123","Ramirez","sant","456789");
       //    Vendedor vendedor2 =marketPlace.crearVendedor("Santiago","123","Ramirez","sant","456789");
       // Administrador administrador = marketPlace.crearAdministrador("Juan PABLO","123","Ramirez","sant","456789");
 //       Administrador administrador1 = marketPlace.crearAdministrador("Felipe",null,null,null,null);
   //     Producto producto = marketPlace.crearProducto("ProductoPrueba","1234","Imagen","Cereales",1000,"Un producto de prueba",1,vendedor1);

        //marketPlace.comprarProducto(producto,1);
            Server server = new Server(8082);
            server.run();

        } catch (Exception e) {
            Persistencia.guardaRegistroLog(e.getMessage(),1,"Prueba");
        }


    }
}
