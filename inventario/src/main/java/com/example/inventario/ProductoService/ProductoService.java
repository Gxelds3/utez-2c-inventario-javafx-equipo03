package com.example.inventario.ProductoService;

import com.example.inventario.Model.Producto;
import com.example.inventario.ProductoFile.ProductoFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {


    ProductoFile file = new ProductoFile();

    public List<Producto> loadForListView() throws IOException {
        List<String> lines = file.readAllLines();
        List<Producto> result = new ArrayList<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) continue;

            String[] parts = line.split("-");

            if (parts.length < 5) {
                System.out.println("Línea inválida (faltan datos): " + line);
                continue;
            }

            try {
                String codigo = parts[0];
                String nombre = parts[1];
                double precio = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3]);
                String categoria = parts[4];
                result.add(new Producto(codigo, nombre, precio, stock, categoria));

            } catch (NumberFormatException e) {
                System.out.println("Error de formato numérico en la línea: " + line);
            }
        }
        return result;
    }
    public String Agregar(String codigo, String nombre, String precio, String stock, String categoria) throws IOException {
        Boolean codigorepetido = file.BuscarCodigo(codigo);
        if (codigorepetido){
            return "El codigo esta repetido";
        }
        if (codigo == null || codigo.isBlank() ){
            return "El codigo esta vacio";
        }
        if (nombre == null || nombre.isBlank() ){
            return "El nombre esta vacio";
        }
        if (nombre.length() < 3 ){
            return "El nombre es muy corto";
        }
        if (precio.isBlank() ){
            return "El precio esta vacio";
        }

        if (stock.isBlank()){
            return "El stock esta vacio";
        }

        if (categoria == null  || categoria.isBlank()){
            return "La categoria esta vacio";
        }

        try {
            double preVal = Double.parseDouble(precio);
            int stockVal = Integer.parseInt(stock);

            if (preVal < 0) return "El precio no puede ser menor a 0";
            if (stockVal < 0) return "El stock no puede ser menor a 0";

        } catch (NumberFormatException e) {
            return "El precio o stock deben ser valores numéricos";
        }

        file.addNewLine(codigo+"-"+nombre+"-"+precio+"-"+stock+"-"+categoria);
        return "Producto agregado";
    }

    public void editarProducto(String codigo, String nombre, String precio, String stock, String categoria) throws IOException {
        List<String> todasLasLineas = file.readAllLines();
        List<String> nuevaLista = new ArrayList<>();

        for (String linea : todasLasLineas) {
            if (linea == null || linea.isBlank()) continue;

            String[] parts = linea.split("-");
            if (parts[0].equals(codigo)) {
                nuevaLista.add(codigo + "-" + nombre + "-" + precio + "-" + stock + "-" + categoria);
            } else {
                nuevaLista.add(linea);
            }
        }
        file.saveFile(nuevaLista);
    }


    public void eliminarProducto(String item) throws IOException {
        String[] parts = item.split("-");
        String Cod = parts[0];
        file.eliminarPorCodigo(Cod);
    }



}
