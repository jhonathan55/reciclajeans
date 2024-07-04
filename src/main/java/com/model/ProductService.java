package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private ArrayList<Producto> listaProductos = new ArrayList<>();

    public void ProductoServicio() {
        listaProductos = new ArrayList<>();
    }

    public ArrayList<Producto> getListaProductos() {
        if (listaProductos == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si la lista de productos no está inicializada.
        }
        return new ArrayList<>(listaProductos); // Devuelve una copia de la lista para evitar modificaciones externas.
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    // Método para añadir productos a la lista
    public void addProducto(Producto producto) {
        listaProductos.add(producto);
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        return listaProductos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null); // Devuelve null si no se encuentra el producto
    }

    public boolean esCampoValido(String campo) {
        List<String> camposValidos = Arrays.asList("articulo", "precio", "descripcion", "talla", "marca", "color");
        return camposValidos.contains(campo.toLowerCase());
    }

    public boolean editProducto(Producto producto, String campo, String nuevoValor) {
        switch (campo.toLowerCase()) {
            case "articulo":
                producto.setArticulo(nuevoValor);
                break;
            case "precio":
                producto.setPrecio(nuevoValor);
                break;
            case "descripcion":
                producto.setDescripcion(nuevoValor);
                break;
            case "talla":
                producto.setTalla(nuevoValor);
                break;
            case "marca":
                producto.setMarca(nuevoValor);
                break;
            case "color":
                producto.setColor(nuevoValor);
                break;
            default:
                return false; // Nunca debe llegar aquí si esCampoValido funciona correctamente
        }
        return true; // Indica éxito
    }

    // Método para cargar productos desde un archivo CSV
    public void cargarProductosDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // Esto es para saltar la línea de encabezados si la hay
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // Asegúrate de que el delimitador sea el correcto
                if (datos.length == 7) {
                    Producto producto = new Producto(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5],
                            datos[6]);
                    addProducto(producto);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

}
