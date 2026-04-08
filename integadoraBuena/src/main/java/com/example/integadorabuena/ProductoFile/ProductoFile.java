package com.example.integadorabuena.ProductoFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ProductoFile {

    private final Path pathFile = Paths.get("data", "Productos.csv");


    private void ensureFile() throws IOException {

        if(Files.notExists(pathFile)){
            Files.createFile(pathFile);
        }
    }

    public List<String> readAllLines() throws IOException {
        return Files.readAllLines(pathFile, StandardCharsets.UTF_8);
    }

    public void addNewLine(String line) throws IOException {
        ensureFile();
        Files.writeString(pathFile, line+System.lineSeparator(),
                StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    public void saveFile(List<String> lines ) throws IOException {
        Files.write(pathFile, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void eliminarPorCodigo(String codigo) throws IOException {
        List<String> lineas = readAllLines();
        lineas.removeIf(linea -> {
            if (linea == null || linea.isBlank()) return false;
            String[] partes = linea.split("-");
            return partes.length > 0 && partes[0].equals(codigo);
        });

        saveFile(lineas);
    }

    public boolean BuscarCodigo(String codigo) throws IOException {
        List<String> lineas = readAllLines();
        for(String linea : lineas){
            String[] partes = linea.split("-");
            if (partes.length > 0 && partes[0].equals(codigo)){
                return true;
            }
        }
        return false;
    }



}