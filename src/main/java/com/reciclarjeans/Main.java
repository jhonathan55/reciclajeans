package com.reciclarjeans;

import java.util.Scanner;

import com.model.ArchivoService;
import com.model.Menu;
import com.model.ProductService;
import com.model.Producto;

public class Main {
    public static void main(String[] args) {
        ProductService servicio = new ProductService();
        ArchivoService archivoService = new ArchivoService();

        Menu menu = new Menu(servicio, archivoService);
        menu.displayMenu();
    }
}