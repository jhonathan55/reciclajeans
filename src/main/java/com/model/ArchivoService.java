package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import com.google.gson.Gson;

public class ArchivoService {
    public void cargarDatos(String rutaArchivo, ProductService servicio) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                Producto producto = new Producto();
                producto.setArticulo(datos[0]);
                producto.setPrecio(datos[1]);
                producto.setDescripcion(datos[2]);
                producto.setCodigo(datos[3]);
                producto.setTalla(datos[4]);
                producto.setMarca(datos[5]);
                producto.setColor(datos[6]);
                servicio.addProducto(producto);
            }
            System.out.println("Datos cargados con éxito.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void createCarpeta(String nombreCarpeta) {
        File file = new File(nombreCarpeta);
        if (file.exists()) {
            System.out.println("La carpeta ya existe.");
        } else {
            if (file.mkdirs()) {
                System.out.println("Carpeta creada con éxito.");
            } else {
                System.out.println("No se pudo crear la carpeta.");
            }
        }
    }

    public void createFile(String nombreArchivo) {
        File file = new File(nombreArchivo);
        try {
            if (file.exists()) {
                System.out.println("El archivo ya existe.");
            } else {
                if (file.createNewFile()) {
                    System.out.println("Archivo creado con éxito.");
                } else {
                    System.out.println("El archivo ya existe.");
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el archivo: " + e.getMessage());
        }
    }

    public void guardarDataJson(String ruta, Object data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        File archivo = new File(ruta);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(archivo, false));
            pw.println(json);
            pw.close();
            System.out.println("Data guardada");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void guardarDataCSV(String rutaArchivo, ArrayList<Producto> productos) {
        boolean fileExists = new File(rutaArchivo).exists();
        try (FileWriter writer = new FileWriter(rutaArchivo,true)) {
            // Escribir los encabezados del CSV
          
            if (!fileExists) {
                writer.append("Articulo,Precio,Descripcion,Codigo,Talla,Marca,Color\n");
            }
            // Iterar sobre cada producto y escribir sus datos en el archivo CSV
            for (Producto producto : productos) {
                writer.append(producto.getArticulo()).append(",");
                writer.append(producto.getPrecio()).append(",");
                writer.append(producto.getDescripcion()).append(",");
                writer.append(producto.getCodigo()).append(",");
                writer.append(producto.getTalla()).append(",");
                writer.append(producto.getMarca()).append(",");
                writer.append(producto.getColor()).append("\n");
            }
            writer.flush(); // Asegurarse de que todos los datos se escriban en el archivo
            System.out.println("Datos guardados en el archivo CSV correctamente.");
        } catch (IOException e) {
            if (e.getMessage()
                    .contains("The process cannot access the file because it is being used by another process")) {
                System.out.println(
                        "El archivo CSV no está accesible porque está siendo utilizado por otro proceso. Intente nuevamente más tarde.");
            } else {
                System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
            }
        }
    }

    public void guardarDataTxt(String rutaArchivo, ArrayList<Producto> productos) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (Producto producto : productos) {
                writer.write(producto.getArticulo() + "\n");
                writer.write(producto.getPrecio() + "\n");
                writer.write(producto.getDescripcion() + "\n");
                writer.write(producto.getCodigo() + "\n");
                writer.write(producto.getTalla() + "\n");
                writer.write(producto.getMarca() + "\n");
                writer.write(producto.getColor() + "\n");
            }
            System.out.println("Datos guardados en el archivo de texto correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de texto: " + e.getMessage());
        }
    }
}
