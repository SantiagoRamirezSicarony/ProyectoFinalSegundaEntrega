package servidor.proyectofinalservidor.Modelos;


import servidor.proyectofinalservidor.Excepciones.VendedorNoEncontradoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RedVendedores implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Vendedor> vendedores;



    private RedVendedores() {
        this.vendedores = new ArrayList<>();
    }
    private RedVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }



    //Excepcion 7

    public Vendedor buscarVendedorPorNombre(String nombre) throws VendedorNoEncontradoException {
        for (Vendedor v : vendedores) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        throw new VendedorNoEncontradoException("El vendedor con nombre " + nombre + " no fue encontrado.");
    }
    



    // Builder
    public static class Builder {
        private ArrayList<Vendedor> vendedores = new ArrayList<>();

        public Builder() {
        }



        // Método para añadir una lista de vendedores
        public Builder setVendedores(ArrayList<Vendedor> vendedores) {
            this.vendedores = vendedores;
            return this;
        }

        // Método build para crear un RedVendedores
        public RedVendedores build() {
            return new RedVendedores(vendedores);
        }
    }
    @Override
    public String toString() {
        return "RedVendedores{" +
                "vendedores=" + vendedores +
                '}';
    }
}


