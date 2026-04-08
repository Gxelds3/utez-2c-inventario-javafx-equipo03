module com.example.inventario {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.inventario to javafx.fxml;
    exports com.example.inventario;

    opens com.example.inventario.ProductoService to javafx.fxml;
    exports com.example.inventario.ProductoService;

    opens com.example.inventario.Model to javafx.fxml;
    exports com.example.inventario.Model;

    exports com.example.inventario.Controllers;
    opens com.example.inventario.Controllers to javafx.fxml;

}