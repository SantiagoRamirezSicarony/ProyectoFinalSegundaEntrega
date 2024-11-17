package servidor.proyectofinalservidor.Modelos;

import java.io.Serializable;
import java.util.Date;
public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String contenidoTexto;
    private byte[] contenidoImagen;
    private Date fecha;
    private Vendedor remitente;
    private Vendedor receptor;

    // Constructor completo
    public Mensaje(String contenidoTexto, byte[] contenidoImagen, Date fecha, Vendedor remitente, Vendedor receptor) {
        this.contenidoTexto = contenidoTexto;
        this.contenidoImagen = contenidoImagen;
        this.fecha = fecha;
        this.remitente = remitente;
        this.receptor = receptor;
    }

    // Getters
    public String getContenidoTexto() {
        return contenidoTexto;
    }

    public byte[] getContenidoImagen() {
        return contenidoImagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public Vendedor getRemitente() {
        return remitente;
    }

    public Vendedor getReceptor() {
        return receptor;
    }

    // Setters
    public void setContenidoTexto(String contenidoTexto) {
        this.contenidoTexto = contenidoTexto;
    }

    public void setContenidoImagen(byte[] contenidoImagen) {
        this.contenidoImagen = contenidoImagen;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setRemitente(Vendedor remitente) {
        this.remitente = remitente;
    }

    public void setReceptor(Vendedor receptor) {
        this.receptor = receptor;
    }

    // toString
    @Override
    public String toString() {
        return "Mensaje{" +
                "contenidoTexto='" + contenidoTexto + '\'' +
                ", contenidoImagen=" + (contenidoImagen != null ? "Image Data" : "No Image") +
                ", fecha=" + fecha +
                ", remitente=" + remitente +
                ", receptor=" + receptor +
                '}';
    }

    // Builder
    public static class Builder {
        private String contenidoTexto;
        private byte[] contenidoImagen;
        private Date fecha;
        private Vendedor remitente;
        private Vendedor receptor;

        public Builder setContenidoTexto(String contenidoTexto) {
            this.contenidoTexto = contenidoTexto;
            return this;
        }

        public Builder setContenidoImagen(byte[] contenidoImagen) {
            this.contenidoImagen = contenidoImagen;
            return this;
        }

        public Builder setFecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder setRemitente(Vendedor remitente) {
            this.remitente = remitente;
            return this;
        }

        public Builder setReceptor(Vendedor receptor) {
            this.receptor = receptor;
            return this;
        }

        public Mensaje build() {
            return new Mensaje(contenidoTexto, contenidoImagen, fecha, remitente, receptor);
        }
    }
}


