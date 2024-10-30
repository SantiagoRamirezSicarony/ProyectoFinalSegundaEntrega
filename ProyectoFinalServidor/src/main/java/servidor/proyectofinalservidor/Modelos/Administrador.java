package servidor.proyectofinalservidor.Modelos;


import java.io.Serializable;

public class Administrador extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String correo;
    private String contrasenia;
    public Administrador() {}
    // Constructor de Administrador
    public Administrador(String nombre, String cedula, String apellido, String correo, String contrasenia) {
        super(nombre, cedula, apellido); // Llamada al constructor de Persona
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    // Getter y Setter para correo y contrasenia
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // toString para Administrador
    @Override
    public String toString() {
        return "Administrador{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", cedula='" + getCedula() + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

    // Builder para Administrador
    public static class Builder extends Persona.Builder {
        private String correo;
        private String contrasenia;

        public Builder() {
            super();  // Llamada al constructor de Persona.Builder
        }

        // Sobrescribir el tipo de retorno en los métodos heredados
        @Override
        public Builder setNombre(String nombre) {
            super.setNombre(nombre);
            return this;
        }

        @Override
        public Builder setApellido(String apellido) {
            super.setApellido(apellido);
            return this;
        }

        @Override
        public Builder setCedula(String cedula) {
            super.setCedula(cedula);
            return this;
        }

        // Métodos específicos del Builder de Administrador
        public Builder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
            return this;
        }

        // Método build para crear un Administrador
        @Override
        public Administrador build() {
            return new Administrador(nombre, cedula, apellido, correo, contrasenia);
        }
    }
}

