package com.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ProductService servicio;
    private ArchivoService archivoService;
    private Scanner scanner;

    public Menu() {
    }

    public Menu(ProductService servicio, ArchivoService archivoService) {
        this.servicio = servicio;
        this.archivoService = archivoService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        int opcion;
        do {
            System.out.println("1 Listar Producto\n2 Editar Datos\n3 Importar Datos\n4 agregar\n" +
                    "5 Exportar Datos\n6 salir\n" + //
                    "Ingrese una opción:");
            opcion = scanner.nextInt();
            processOption(opcion);
        } while (opcion != 6);
        scanner.close();
    }

    private void processOption(int opcion) {
        switch (opcion) {
            case 1:
                if (servicio.getListaProductos().isEmpty()) {
                    System.out.println("No hay productos cargados.");
                } else {
                    servicio.getListaProductos().forEach(System.out::println);
                }
                break;
            case 2:
                editProduct();
                break;
            case 3:
                importData();
                break;
            case 4:
                System.out.println("Agregar producto");
                System.out.println("Ingrese el nombre del producto");
                String articulo = scanner.next();
                System.out.println("Ingrese el precio del producto");
                String precio = scanner.next();
                System.out.println("Ingrese la descripción del producto");
                String descripcion = scanner.next();
                System.out.println("Ingrese el código del producto");
                String codigo = scanner.next();
                System.out.println("Ingrese la talla del producto");
                String talla = scanner.next();
                System.out.println("Ingrese la marca del producto");
                String marca = scanner.next();
                System.out.println("Ingrese el color del producto");
                String color = scanner.next();
                Producto producto = new Producto(articulo, precio, descripcion, codigo, talla, marca, color);
                servicio.addProducto(producto);
                break;
            case 5:
                System.out.println("Exportar Datos");
                boolean bandera = true;
                while (bandera) {
                    System.out.println("1. Exportar a JSON");
                    System.out.println("2. Exportar a CSV");
                    System.out.println("3. Exportar a Txt");
                    System.out.println("4. Salir");
                    int option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("guardando data JSON");
                            break;
                        case 2:
                            System.out.println("guardando data CSV");
                            archivoService.createCarpeta("C:/Users/jhona/Desktop/excel");
                            archivoService.createFile("C:/Users/jhona/Desktop/excel/ProductoExportado.csv");
                            ArrayList<Producto> listaProductos = servicio.getListaProductos();
                            archivoService.guardarDataCSV("C:/Users/jhona/Desktop/excel/ProductoExportado.csv",
                                    listaProductos);
                            break;
                        case 3:
                            System.out.println("guardando data Txt");
                            archivoService.createCarpeta("C:/Users/jhona/Desktop/excel");
                            archivoService.createFile("C:/Users/jhona/Desktop/excel/ProductoExportado.txt");
                            archivoService.guardarDataTxt("C:/Users/jhona/Desktop/excel/ProductoExportado.txt",
                                    servicio.getListaProductos());
                            break;
                        case 4:
                            System.out.println("Saliendo...");
                            bandera = false;
                            break;

                        default:
                            break;

                    }
                }

                break;
            case 6:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    private void editProduct() {
        System.out.println("Ingrese el código del producto a editar:");
        String codigo = scanner.next();

        // Verificar si el producto existe antes de continuar
        Producto producto = servicio.buscarProductoPorCodigo(codigo);
        if (producto == null) {
            System.out.println("Error: No se encontró un producto con el código proporcionado.");
            return; // Salir del método si no se encuentra el producto
        }

        System.out.println("Ingrese el campo a editar (articulo, precio, descripcion, talla, marca, color):");
        String campo = scanner.next();

        // Verificar si el campo es válido antes de continuar
        if (!servicio.esCampoValido(campo)) {
            System.out.println("Error: El campo especificado no es válido.");
            return; // Salir del método si el campo no es válido
        }

        System.out.println("Ingrese el nuevo valor para el campo:");
        String nuevoValor = scanner.next();

        // Intentar editar el producto
        if (servicio.editProducto(producto, campo, nuevoValor)) {
            System.out.println("Producto actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el producto.");
        }
    }

    private void importData() {
        String pathFile = "C:/Users/jhona/Desktop/New folder (2)/ProductosImportados.csv";
        servicio.cargarProductosDesdeCSV(pathFile);
    }
}
