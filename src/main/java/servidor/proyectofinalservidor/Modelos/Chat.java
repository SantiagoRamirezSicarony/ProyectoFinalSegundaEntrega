package servidor.proyectofinalservidor.Modelos;


import servidor.proyectofinalservidor.Excepciones.MensajeInvalidoException;

import java.io.Serializable;
import java.util.ArrayList;
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Mensaje> mensajes;
    private ArrayList<Persona> participantes;

    // Constructor vacío
    public Chat() {
        this.mensajes = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    // Constructor lleno
    public Chat(ArrayList<Mensaje> mensajes, ArrayList<Persona> participantes) {
        this.mensajes = mensajes;
        this.participantes = participantes;
    }

    // Getters
    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public ArrayList<Persona> getParticipantes() {
        return participantes;
    }

    // Setters
    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void setParticipantes(ArrayList<Persona> participantes) {
        this.participantes = participantes;
    }

    // Método toString
    @Override
    public String toString() {
        return "Chat{" +
                "mensajes=" + mensajes +
                ", participantes=" + participantes +
                '}';
    }

    // Builder Pattern
    public static class Builder {
        private ArrayList<Mensaje> mensajes = new ArrayList<>();
        private ArrayList<Persona> participantes = new ArrayList<>();

        public Builder setMensajes(ArrayList<Mensaje> mensajes) {
            this.mensajes = mensajes;
            return this;
        }

        public Builder setParticipantes(ArrayList<Persona> participantes) {
            this.participantes = participantes;
            return this;
        }

        public Chat build() {
            return new Chat(mensajes, participantes);
        }
    }
}