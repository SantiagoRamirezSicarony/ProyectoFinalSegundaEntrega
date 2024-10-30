package servidor.proyectofinalservidor.Modelos;



import servidor.proyectofinalservidor.Excepciones.ProductoSinDescripcionException;
import servidor.proyectofinalservidor.Excepciones.ProductoSinPrecioException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String codigo;
    private String imagen;
    private String categoria;
    private double precio;
    private String descripcion;
    private EstadoProducto estado;
    private List<Comentario> comentarios;
    private List<MeGusta> meGustas;
    private int cantidad;

    
    public Producto() {}
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setMeGustas(List<MeGusta> meGustas) {
        this.meGustas = meGustas;
    }

    public void setCantidad(int cantidad) {this.cantidad = cantidad;}


    //Excepcion 6
    public void validarDescripcion() throws ProductoSinDescripcionException {
        if (this.descripcion == null || this.descripcion.trim().isEmpty()) {
            throw new ProductoSinDescripcionException("El producto debe tener una descripción.");
        }
    }

    //Excepcion 10

    public void verificarPrecio() throws ProductoSinPrecioException {
        if (this.precio <= 0) {
            throw new ProductoSinPrecioException("El producto " + this.nombre + " no tiene un precio válido.");
        }
    }

    

    // Constructor con builder
    private Producto(ProductoBuilder builder) {
        this.nombre = builder.nombre;
        this.codigo = builder.codigo;
        this.imagen = builder.imagen;
        this.categoria = builder.categoria;
        this.precio = builder.precio;
        this.descripcion = builder.descripcion;
        this.estado = builder.estado;
        this.comentarios = builder.comentarios;
        this.meGustas = builder.meGustas;
        this.cantidad = builder.cantidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public List<MeGusta> getMeGustas() {
        return meGustas;
    }

    public int getCantidad() {return cantidad;}

    // Builder
    public static class ProductoBuilder {
        private String nombre;
        private String codigo;
        private String imagen;
        private String categoria;
        private double precio;
        private String descripcion;
        private EstadoProducto estado;
        private List<Comentario> comentarios = new ArrayList<>();
        private List<MeGusta> meGustas = new ArrayList<>();
        private int cantidad;

        public ProductoBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public ProductoBuilder setCantidad(int cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public ProductoBuilder setCodigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public ProductoBuilder setImagen(String imagen) {
            this.imagen = imagen;
            return this;
        }

        public ProductoBuilder setCategoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public ProductoBuilder setPrecio(double precio) {
            this.precio = precio;
            return this;
        }

        public ProductoBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public ProductoBuilder setEstado(EstadoProducto estado) {
            this.estado = estado;
            return this;
        }


        public ProductoBuilder setComentarios(List<Comentario> comentarios) {
            this.comentarios = comentarios;
            return this;
        }

        public ProductoBuilder setMeGustas(List<MeGusta> meGustas) {
            this.meGustas = meGustas;
            return this;
        }

        public Producto build() {
            return new Producto(this);
        }
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                ", comentarios=" + comentarios +
                ", meGustas=" + meGustas +
                '}';
    }

    public void vender() {
        this.estado = EstadoProducto.VENDIDO;  // Cambia el estado a VENDIDO
    }
    

}
