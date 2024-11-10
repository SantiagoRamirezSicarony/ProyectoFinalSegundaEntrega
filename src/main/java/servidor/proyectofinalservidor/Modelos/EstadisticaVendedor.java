package servidor.proyectofinalservidor.Modelos;


import servidor.proyectofinalservidor.Excepciones.EstadisticaNoDisponibleException;

import java.io.Serializable;

public class EstadisticaVendedor implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productosVendidos;
    private int productosPublicados;
    private int mensajesEnviados;
    public EstadisticaVendedor() {}
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(int productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public int getProductosPublicados() {
        return productosPublicados;
    }

    public void setProductosPublicados(int productosPublicados) {
        this.productosPublicados = productosPublicados;
    }

    public int getMensajesEnviados() {
        return mensajesEnviados;
    }

    public void setMensajesEnviados(int mensajesEnviados) {
        this.mensajesEnviados = mensajesEnviados;
    }

    //Excepcion 9
    public void verificarEstadisticas() throws EstadisticaNoDisponibleException {
        if (this.productosVendidos == 0 && this.mensajesEnviados == 0) {
            throw new EstadisticaNoDisponibleException("No hay estadísticas disponibles para este vendedor.");
        }
        System.out.println("Estadísticas disponibles.");
    }
    

    // Constructor con builder
    private EstadisticaVendedor(EstadisticaVendedorBuilder builder) {
        this.productosVendidos = builder.productosVendidos;
        this.productosPublicados = builder.productosPublicados;
        this.mensajesEnviados = builder.mensajesEnviados;
    }

    // Builder
    public static class EstadisticaVendedorBuilder {
        private int productosVendidos;
        private int productosPublicados;
        private int mensajesEnviados;

        public EstadisticaVendedorBuilder setProductosVendidos(int productosVendidos) {
            this.productosVendidos = productosVendidos;
            return this;
        }

        public EstadisticaVendedorBuilder setProductosPublicados(int productosPublicados) {
            this.productosPublicados = productosPublicados;
            return this;
        }

        public EstadisticaVendedorBuilder setMensajesEnviados(int mensajesEnviados) {
            this.mensajesEnviados = mensajesEnviados;
            return this;
        }

        public EstadisticaVendedor build() {
            return new EstadisticaVendedor(this);
        }
    }

    @Override
    public String toString() {
        return "EstadisticaVendedor{" +
                "productosVendidos=" + productosVendidos +
                ", productosPublicados=" + productosPublicados +
                ", mensajesEnviados=" + mensajesEnviados +
                '}';
    }
}
