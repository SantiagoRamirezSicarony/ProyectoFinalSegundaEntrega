package servidor.proyectofinalservidor.Modelos;


import servidor.proyectofinalservidor.Excepciones.MensajeInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Mensaje> mensajes;
    private ArrayList<Persona> participantes;
    public Chat() {}
    // Constructor con builder
    private Chat(ChatBuilder builder) {
        this.mensajes = builder.mensajes;
        this.participantes = builder.participantes;
    }

    //Excepcion 5
    public void enviarMensaje(Mensaje mensaje) throws MensajeInvalidoException {
        if (mensaje.getContenidoTexto() == null || mensaje.getContenidoTexto().trim().isEmpty()) {
            throw new MensajeInvalidoException("El mensaje no puede estar vacío.");
        }
        mensajes.add(mensaje);
        System.out.println("Mensaje enviado correctamente.");
    }
        

    // Builder
    public static class ChatBuilder {
        private ArrayList<Mensaje> mensajes = new ArrayList<>();
        private ArrayList<Persona> participantes = new ArrayList<>();

        public ChatBuilder setMensajes(ArrayList<Mensaje> mensajes) {
            this.mensajes = mensajes;
            return this;
        }

        public ChatBuilder setParticipantes(ArrayList<Persona> participantes) {
            this.participantes = participantes;
            return this;
        }

        public Chat build() {
            return new Chat(this);
        }
    }

    @Override
    public String toString() {
        return "Chat{" +
                "mensajes=" + mensajes +
                ", participantes=" + participantes +
                '}';
    }
}
