package servidor.proyectofinalservidor.Modelos;

import java.io.Serializable;
import java.util.Date;

public class MeGusta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Date fecha;
    private Vendedor autor;
    private Producto producto;
    public MeGusta() {}
    // Constructor con builder
    private MeGusta(MeGustaBuilder builder) {
        this.fecha = builder.fecha;
        this.autor = builder.autor;
        this.producto = builder.producto;
    }

    // Builder
    public static class MeGustaBuilder {
        private Date fecha;
        private Vendedor autor;
        private Producto producto;

        public MeGustaBuilder setFecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public MeGustaBuilder setAutor(Vendedor autor) {
            this.autor = autor;
            return this;
        }

        public MeGustaBuilder setProducto(Producto producto) {
            this.producto = producto;
            return this;
        }

        public MeGusta build() {
            return new MeGusta(this);
        }
    }

    @Override
    public String toString() {
        return "MeGusta{" +
                "fecha=" + fecha +
                ", autor=" + autor +
                ", producto=" + producto +
                '}';
    }
}

