module com.example.invetario {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.invetario to javafx.fxml;
    exports com.example.invetario;
}