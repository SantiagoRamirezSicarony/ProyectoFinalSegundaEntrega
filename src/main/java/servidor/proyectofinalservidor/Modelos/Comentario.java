package servidor.proyectofinalservidor.Modelos;

import java.io.Serializable;
import java.util.Date;

public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mensaje;
    private Date fecha;
    private Persona autor;
    private Producto producto;
    public Comentario() {}
    // Constructor
    public Comentario(String mensaje, Date fecha, Persona autor, Producto producto) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.autor = autor;
        this.producto = producto;

    }

    // Getters
    public String getMensaje() {
        return mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public Persona getAutor() {
        return autor;
    }

    public Producto getProducto() {
        return producto;
    }

    // Setters
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // toString method
    @Override
    public String toString() {
        return  fecha + "\n" + autor.getNombre() + " " + autor.getApellido() + ":\n" + mensaje;

    }

    // Builder pattern
    public static class Builder {
        private String mensaje;
        private Date fecha;
        private Persona autor;
        private Producto producto;

        public Builder() {
        }

        public Builder setMensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder setFecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder setAutor(Persona autor) {
            this.autor = autor;
            return this;
        }

        public Builder setProducto(Producto producto) {
            this.producto = producto;
            return this;
        }


        public Comentario build() {
            return new Comentario(mensaje, fecha, autor, producto);
        }
    }
}

