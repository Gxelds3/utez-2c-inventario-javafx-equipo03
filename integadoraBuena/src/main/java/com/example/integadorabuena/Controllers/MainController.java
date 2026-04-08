package com.example.integadorabuena.Controllers;

import com.example.integadorabuena.Model.Producto; // Asegúrate de que la ruta sea correcta
import com.example.integadorabuena.ProductoService.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {

    ProductoService service = new ProductoService();


    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> Codigo;
    @FXML private TableColumn<Producto, String> Nombre;
    @FXML private TableColumn<Producto, Double> Precio;
    @FXML private TableColumn<Producto, Integer> Stock;
    @FXML private TableColumn<Producto, String> Categoria;
    @FXML private TextField txtBuscar;

    private ObservableList<Producto> productList = FXCollections.observableArrayList();
    private Producto itemSeleccionado;

    @FXML
    public void initialize() throws IOException {

        Codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        Precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        Categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tablaProductos.setItems(productList);
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            buscar();
        });

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, value) -> {
            this.itemSeleccionado = value;
        });

        recargar();
    }

    @FXML
    public void buscar() {
        String filtro = txtBuscar.getText().trim().toLowerCase();

        if (filtro.isEmpty()) {
            tablaProductos.setItems(productList);
            return;
        }

        ObservableList<Producto> resultados = FXCollections.observableArrayList();

        for (Producto p : productList) {
            if (p.getCodigo().toLowerCase().contains(filtro) ||
                    p.getNombre().toLowerCase().contains(filtro)) {
                resultados.add(p);
            }
        }
        tablaProductos.setItems(resultados);
    }

    @FXML
    public void nuevo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integadorabuena/views/form-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nuevo Producto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            recargar();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void editar() throws IOException {
        if (itemSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/integadorabuena/views/editarProducto.fxml"));
            Parent root = loader.load();
            FormController form = loader.getController();
            form.datosEditar(itemSeleccionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            recargar();
        } else {
            mostrarAlerta("Atención", null, "Selecciona un producto para editar.", Alert.AlertType.WARNING);
        }
    }

    public void eliminar() throws IOException {
        if (itemSeleccionado == null) {
            mostrarAlerta("Error", null, "Selecciona un producto para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        service.eliminarProducto(itemSeleccionado.getCodigo());
        recargar();
        mostrarAlerta("Éxito", null, "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
    }

    public void recargar() throws IOException {
        txtBuscar.clear();

        List<Producto> items = service.loadForListView();
        productList.setAll(items);
        tablaProductos.setItems(productList);
    }

    private void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}