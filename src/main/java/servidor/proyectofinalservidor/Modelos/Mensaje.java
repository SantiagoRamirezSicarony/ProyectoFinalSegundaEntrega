package servidor.proyectofinalservidor.Modelos;

import java.io.Serializable;
import java.util.Date;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String contenidoTexto;
    private String contenidoImagen;
    private Date fecha;
    private Vendedor remitente;
    private Vendedor receptor;

    // Constructor con builder
    private Mensaje(MensajeBuilder builder) {
        this.contenidoTexto = builder.contenidoTexto;
        this.contenidoImagen = builder.contenidoImagen;
        this.fecha = builder.fecha;
        this.remitente = builder.remitente;
        this.receptor = builder.receptor;
    }

    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getContenidoTexto() {
        return contenidoTexto;
    }

    public void setContenidoTexto(String contenidoTexto) {
        this.contenidoTexto = contenidoTexto;
    }

    public String getContenidoImagen() {
        return contenidoImagen;
    }

    public void setContenidoImagen(String contenidoImagen) {
        this.contenidoImagen = contenidoImagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Vendedor getRemitente() {
        return remitente;
    }

    public void setRemitente(Vendedor remitente) {
        this.remitente = remitente;
    }

    public Vendedor getReceptor() {
        return receptor;
    }

    public void setReceptor(Vendedor receptor) {
        this.receptor = receptor;
    }



    // Builder
    public static class MensajeBuilder {
        private String contenidoTexto;
        private String contenidoImagen;
        private Date fecha;
        private Vendedor remitente;
        private Vendedor receptor;

        public MensajeBuilder setContenidoTexto(String contenidoTexto) {
            this.contenidoTexto = contenidoTexto;
            return this;
        }

        public MensajeBuilder setContenidoImagen(String contenidoImagen) {
            this.contenidoImagen = contenidoImagen;
            return this;
        }

        public MensajeBuilder setFecha(Date fecha) {
            this.fecha = fecha;
            return this;
        }

        public MensajeBuilder setRemitente(Vendedor remitente) {
            this.remitente = remitente;
            return this;
        }

        public MensajeBuilder setReceptor(Vendedor receptor) {
            this.receptor = receptor;
            return this;
        }

        public Mensaje build() {
            return new Mensaje(this);
        }
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "contenidoTexto='" + contenidoTexto + '\'' +
                ", contenidoImagen='" + contenidoImagen + '\'' +
                ", fecha=" + fecha +
                ", remitente=" + remitente +
                ", receptor=" + receptor +
                '}';
    }
}
