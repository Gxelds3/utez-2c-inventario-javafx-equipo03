package com.example.integadorabuena.Controllers;

import com.example.integadorabuena.Model.Producto;
import com.example.integadorabuena.ProductoService.ProductoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormController {

    ProductoService service = new ProductoService();

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private ComboBox<String> txtcategoria;

    @FXML
    private void initialize(){
        String[] categoria = {
                "Dulces",
                "Bebidas",
                "Cuidado Personal",
                "Limpieza del Hogar",
                "Perecederos",
                "Electrónicos",
                "Papelería y Oficina",
                "Otros"
        };
        txtcategoria.getItems().addAll(categoria);
    }


    public void guardar() throws IOException {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String precioTxt = txtPrecio.getText();
        String stockTxt = txtStock.getText();
        String categoria = txtcategoria.getValue();

        String msj = service.Agregar(codigo,nombre,precioTxt,stockTxt,categoria);
        if (msj.equals("Producto agregado")){
            mostrarAlerta("Exito",msj, Alert.AlertType.INFORMATION);
            return;
        }
        mostrarAlerta("Error",msj, Alert.AlertType.ERROR);
    }

    public void salir() {
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }


    public void cancelar(){
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }

    public void datosEditar(Producto itemSeleccionado) {

        txtCodigo.setText(itemSeleccionado.getCodigo());
        txtNombre.setText(itemSeleccionado.getNombre());


        txtPrecio.setText(String.valueOf(itemSeleccionado.getPrecio()));
        txtStock.setText(String.valueOf(itemSeleccionado.getStock()));


        txtcategoria.setValue(itemSeleccionado.getCategoria());
    }

    public void editarBtn(){
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String precioTxt = txtPrecio.getText();
            String stockTxt = txtStock.getText();
            String categoria = txtcategoria.getValue();
            service.editarProducto(codigo,nombre,precioTxt,stockTxt,categoria);

            Stage stage = (Stage) txtCodigo.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void mostrarAlerta(String titulo, String encabezado, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.showAndWait();
    }

}
