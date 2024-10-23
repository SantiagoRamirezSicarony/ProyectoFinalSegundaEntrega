package servidor.proyectofinalservidor.Modelos;

import co.edu.uniquindio.poo.Excepciones.VendedorNoEncontradoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RedVendedores implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Vendedor> vendedores;
    private final int maxContactos = 10;

    // Constructor con builder
    private RedVendedores(RedVendedoresBuilder builder) {
        this.vendedores = builder.vendedores;
    }

    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public int getMaxContactos() {
        return maxContactos;
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
    public static class RedVendedoresBuilder {
        private List<Vendedor> vendedores = new ArrayList<>();

        public RedVendedoresBuilder setVendedores(List<Vendedor> vendedores) {
            this.vendedores = vendedores;
            return this;
        }

        public RedVendedores build() {
            return new RedVendedores(this);
        }
    }

    @Override
    public String toString() {
        return "RedVendedores{" +
                "vendedores=" + vendedores +
                ", maxContactos=" + maxContactos +
                '}';
    }
}


