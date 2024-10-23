package servidor.proyectofinalservidor.Modelos;

import servidor.proyectofinalservidor.Excepciones.AutenticacionFallidaException;

import java.io.Serializable;

public class Persona implements Serializable {


    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String cedula;

    // Constructor vacío (por si lo necesitas)
    public Persona() {
    }

    // Constructor con parámetros
    public Persona(String nombre, String cedula, String apellido) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.apellido = apellido;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }
    public String getApellido() {
        return apellido;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    // toString method
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                '}';
    }

    // Builder pattern
    public static class Builder {
        private String nombre;
        private String cedula;
        private String apellido;

        public Builder() {
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder setCedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Persona build() {
            return new Persona(nombre, cedula, apellido);
        }
    }


    //Excepcion 8 3l´+

    public void autenticar(String password) throws AutenticacionFallidaException {
        if (!this.password.equals(password)) {
            throw new AutenticacionFallidaException("La autenticación ha fallado. Contraseña incorrecta.");
        }
        System.out.println("Autenticación exitosa.");
    }






}

