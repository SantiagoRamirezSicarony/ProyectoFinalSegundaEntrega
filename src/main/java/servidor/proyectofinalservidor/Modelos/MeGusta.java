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
    public MeGusta(Date fecha, Vendedor autor, Producto producto) {
        this.fecha = fecha;
        this.autor = autor;
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Vendedor getAutor() {
        return autor;
    }

    public void setAutor(Vendedor autor) {
        this.autor = autor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
            return new MeGusta(fecha, autor, producto);
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

