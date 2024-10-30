package servidor.proyectofinalservidor.App;

import servidor.proyectofinalservidor.Modelos.EstadoProducto;
import servidor.proyectofinalservidor.Modelos.MarketPlace;
import servidor.proyectofinalservidor.Modelos.Producto;
import servidor.proyectofinalservidor.Modelos.Vendedor;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        Administrador administrador = new Administrador.Builder().setApellido("Hola")
//                .setCedula("Lo")
//                .setNombre("Logre")
//                .setApellido("Siuuuu").build();
//
//        System.out.println(administrador);
//        ArrayList<Vendedor> vendedors = new ArrayList<Vendedor>();
//
//        RedVendedores red = new RedVendedores.Builder().setVendedores(vendedors).build();
//        System.out.println(red);
//
//        ArrayList<Persona> personas= new ArrayList<>();
//        Persona Andrey =  new Persona("juan","123132","213");
//        personas.add(Andrey);
//        try {
//            Persistencia.guardarObjetosTxt(personas);
//            Persistencia.guardarObjetoTxt(Andrey);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//      "Santiago","123","Ramirez"

        MarketPlace marketPlace = new MarketPlace();
//        marketPlace.crearPersona("Santiago","123","Ramirez");
//
        Producto producto = new Producto.ProductoBuilder().setCategoria("Comida").setCodigo("123").setNombre("Hola").setComentarios(new ArrayList<>())
                .setEstado(EstadoProducto.PUBLICADO).setCantidad(1).build();
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(producto);

        try {
              Vendedor Santiago = marketPlace.crearVendedor("Santiago","123","Sicarony","12345","12345");
              Santiago.setProductos(productos);
              marketPlace.comprarProducto(producto,1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

   // }

        System.out.println(marketPlace.buscarPersona("123","Santiago","Ramirez"));




    }

}
