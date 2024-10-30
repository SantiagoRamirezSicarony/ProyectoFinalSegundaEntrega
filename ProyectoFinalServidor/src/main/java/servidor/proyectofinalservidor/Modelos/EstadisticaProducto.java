package servidor.proyectofinalservidor.Modelos;

import java.io.Serializable;

public class EstadisticaProducto implements Serializable {
    private static final long serialVersionUID = 1L;

    private double calificacionProducto;
    private int meGustas;
    public EstadisticaProducto() {}
    // Constructor con builder
    private EstadisticaProducto(EstadisticaProductoBuilder builder) {
        this.calificacionProducto = builder.calificacionProducto;
        this.meGustas = builder.meGustas;
    }

    // Builder
    public static class EstadisticaProductoBuilder {
        private double calificacionProducto;
        private int meGustas;

        public EstadisticaProductoBuilder setCalificacionProducto(double calificacionProducto) {
            this.calificacionProducto = calificacionProducto;
            return this;
        }

        public EstadisticaProductoBuilder setMeGustas(int meGustas) {
            this.meGustas = meGustas;
            return this;
        }

        public EstadisticaProducto build() {
            return new EstadisticaProducto(this);
        }
    }

    @Override
    public String toString() {
        return "EstadisticaProducto{" +
                "calificacionProducto=" + calificacionProducto +
                ", meGustas=" + meGustas +
                '}';
    }
}
