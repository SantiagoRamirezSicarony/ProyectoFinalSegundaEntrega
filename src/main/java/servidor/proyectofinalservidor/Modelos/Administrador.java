package servidor.proyectofinalservidor.Modelos;


public class Administrador extends Persona {
    private static final long serialVersionUID = 1L;
    private String correo;
    private String contrasenia;

    // Constructor lleno
    public Administrador(String nombre, String cedula, String correo, String contrasenia) {
        super(nombre, cedula);
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
    public Administrador() {}

    // Getters
    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    // Setters
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // toString method
    @Override
    public String toString() {
        return "Administrador{" +
                "nombre=" + super.getNombre() + // Acceder a los atributos de Persona
                ", cedula=" + super.getCedula() +
                ", correo='" + correo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

    // Builder pattern
    public static class Builder {
        private String nombre;
        private String cedula;
        private String correo;
        private String contrasenia;

        public Builder() {
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setCedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
            return this;
        }

        public Administrador build() {
            return new Administrador(nombre, cedula, correo, contrasenia);
        }
    }
}

