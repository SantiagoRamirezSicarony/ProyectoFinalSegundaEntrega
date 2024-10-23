module servidor.proyectofinalservidor {
    requires javafx.controls;
    requires javafx.fxml;


    opens servidor.proyectofinalservidor to javafx.fxml;
    exports servidor.proyectofinalservidor;
}