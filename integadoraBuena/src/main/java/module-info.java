module com.example.integadorabuena {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.integadorabuena to javafx.fxml;
    exports com.example.integadorabuena;

    opens com.example.integadorabuena.Controllers to javafx.fxml;
    exports com.example.integadorabuena.Controllers;

    opens com.example.integadorabuena.ProductoService to javafx.fxml;
    exports com.example.integadorabuena.ProductoService;

    opens com.example.integadorabuena.Model to javafx.fxml;
    exports com.example.integadorabuena.Model;

    opens com.example.integadorabuena.ProductoFile to javafx.fxml;
    exports com.example.integadorabuena.ProductoFile;

}