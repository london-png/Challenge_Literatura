// com.aluracursos.Challenge_Literatura.principal.Principal.java

package com.aluracursos.Challenge_Literatura.principal;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.model.Libro;
import com.aluracursos.Challenge_Literatura.service.*;
import com.aluracursos.Challenge_Literatura.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class Principal {

    private final ConsumoApi consumoApi;
    private final LibroService libroService;
    private final AutorService autorService;
    private final AutorVivoApiService autorVivoApiService;
    private final LibroPorIdiomaService libroPorIdiomaService; // ← Nueva dependencia
    private final Scanner teclado = new Scanner(System.in);
    private final ListViewBook listViewBook;
    private final ListViewActors listViewActors;
    private final ListViewAutoresVivos listViewAutoresVivos;
    private final ListViewLibrosPorIdioma listViewLibrosPorIdioma; // ← Nueva vista

    @Autowired
    public Principal(
            ConsumoApi consumoApi,
            LibroService libroService,
            AutorService autorService,
            AutorVivoApiService autorVivoApiService,
            LibroPorIdiomaService libroPorIdiomaService) { // ← Inyectado
        this.consumoApi = consumoApi;
        this.libroService = libroService;
        this.autorService = autorService;
        this.autorVivoApiService = autorVivoApiService;
        this.libroPorIdiomaService = libroPorIdiomaService; // ← Inicializado
        this.listViewBook = new ListViewBook(libroService);
        this.listViewActors = new ListViewActors(autorService);
        this.listViewAutoresVivos = new ListViewAutoresVivos(autorService);
        this.listViewLibrosPorIdioma = new ListViewLibrosPorIdioma(libroPorIdiomaService); // ← Inicializado
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    **********************************************************
                    ***        ELIJA UNA DE LAS OPCIONES DEL MENÚ          ***
                    ***   1 - Buscar libro por título                      ***
                    ***   2 - Listar libros registrados                    ***
                    ***   3 - Listar autores registrados                   ***
                    ***   4 - Listar autores vivos en un determinado año   ***
                    ***   5 - Listar libros por idioma                     ***
                    ***   0 - Salir                                        ***
                    **********************************************************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> {
                    System.out.println("Escriba el nombre del título que desea buscar:");
                    String titulo = teclado.nextLine().trim();
                    if (!titulo.isEmpty()) {
                        libroService.buscarYMostrarLibros(titulo);
                    } else {
                        System.out.println("No ingresó ningún título. Regresando al menú.");
                    }
                }

                case 2 -> listViewBook.listarLibrosRegistrados();

                case 3 -> listViewActors.ListarAutoresRegistrados();

                // com.aluracursos.Challenge_Literatura.principal.Principal.java

                case 4 -> {
                    System.out.println("Ingrese el año inicial (use números negativos para años a.C., ej. -500):");
                    int yearStart = teclado.nextInt();
                    System.out.println("Ingrese el año final:");
                    int yearEnd = teclado.nextInt();

                    if (yearStart > yearEnd) {
                        System.out.println("❌ Error: El año inicial no puede ser mayor que el año final.");
                    } else {
                        List<Autor> autores = autorService.buscarAutoresVivosEnRango(yearStart, yearEnd);
                        listViewActors.mostrarAutoresVivosEnRango(autores, yearStart, yearEnd);
                    }
                }

                case 5 -> {
                    List<String> idiomas = libroService.obtenerTodosLosIdiomas();
                    if (idiomas.isEmpty()) {
                        System.out.println("\n❌ No hay libros registrados en la base de datos.");
                        break;
                    }

                    listViewBook.mostrarMenuIdiomas(idiomas);
                    System.out.print("Opción: ");
                    opcion = teclado.nextInt();

                    if (opcion > 0 && opcion <= idiomas.size()) {
                        String idiomaSeleccionado = idiomas.get(opcion - 1);
                        List<Libro> libros = libroService.obtenerLibrosPorIdioma(idiomaSeleccionado);
                        listViewBook.mostrarLibrosPorIdioma(libros, idiomaSeleccionado);
                    } else if (opcion != 0) {
                        System.out.println("❌ Opción no válida.");
                    }
                }

                case 0 -> System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");

                default -> System.out.println("Opción no válida. Por favor, elija una opción del menú.");
            }
        }
        // No cerramos el Scanner aquí para evitar IllegalStateException
    }
}