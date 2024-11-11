module servidor.proyectofinalservidor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;


    opens servidor.proyectofinalservidor to javafx.fxml;
    exports servidor.proyectofinalservidor;
    exports servidor.proyectofinalservidor.Modelos;
    exports servidor.proyectofinalservidor.Excepciones;
    exports servidor.proyectofinalservidor.Servicios;
    exports servidor.proyectofinalservidor.Utilidades;



}