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
        String input;
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
            System.out.print("Opción: ");
            input = teclado.nextLine().trim();

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Por favor, ingrese un número del menú.\n");
                pausarYContinuar(); // ← Nueva función
                continue; // Vuelve al inicio del bucle sin ejecutar el switch
            }

            //opcion = teclado.nextInt();
            //teclado.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> {
                    System.out.println("Escriba el nombre del título que desea buscar:");
                    String titulo = teclado.nextLine().trim();
                    if (titulo.isEmpty()) {
                        System.out.println("❌ No ingresó ningún título. Regresando al menú.");
                        pausarYContinuar();

                    //validacion para que en la opcion 1 no afecte el proceso cuando recibe caracteres especiales
                    } else if (!titulo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\-,:;.'\"?!]+")) {
                        System.out.println("❌ El título solo puede contener letras, espacios, tildes y la letra ñ.");
                        pausarYContinuar();
                    } else {
                        libroService.buscarYMostrarLibros(titulo);
                    }
                }

                case 2 -> listViewBook.listarLibrosRegistrados();

                case 3 -> listViewActors.ListarAutoresRegistrados();

                // com.aluracursos.Challenge_Literatura.principal.Principal.java

                case 4 -> {
                    System.out.println("Ingrese el año inicial (use números negativos para años a.C., ej. -500):");
                    // Antes: int yearStart = teclado.nextInt();
                    // Ahora: usamos método seguro que valida entrada
                    int yearStart = leerEnteroValido(); // ✅ Llamada al nuevo método

                    System.out.println("Ingrese el año final:");
                    // Antes: int yearEnd = teclado.nextInt();
                    int yearEnd = leerEnteroValido(); // ✅ Llamada al nuevo método

                    if (yearStart > yearEnd) {
                        System.out.println("❌ Error: El año inicial no puede ser mayor que el año final.");
                    } else {
                        List<Autor> autores = autorService.buscarAutoresVivosEnRango(yearStart, yearEnd);
                        listViewActors.mostrarAutoresVivosEnRango(autores, yearStart, yearEnd);
                    }
                    pausarYContinuar();
                }

                case 5 -> {
                    List<String> idiomas = libroService.obtenerTodosLosIdiomas();
                    if (idiomas.isEmpty()) {
                        System.out.println("\n❌ No hay libros registrados en la base de datos.");
                        break;
                    }

                    listViewBook.mostrarMenuIdiomas(idiomas);
                    System.out.print("Opción: ");
                    int opcionIdioma = leerEnteroValido();

                    if (opcionIdioma == 0) {
                        System.out.println("Volviendo al menú principal...");
                    } else if (opcionIdioma > 0 && opcionIdioma <= idiomas.size()) {
                        String idiomaSeleccionado = idiomas.get(opcionIdioma - 1);
                        List<Libro> libros = libroService.obtenerLibrosPorIdioma(idiomaSeleccionado);
                        listViewBook.mostrarLibrosPorIdioma(libros, idiomaSeleccionado);
                    } else {
                        System.out.println("❌ Opción no válida. Por favor, elija una opción del menú.");
                    }
                }

                case 0 -> System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");

                default -> {
                    System.out.println("Opción no válida. Por favor, elija una opción del menú.");
                    pausarYContinuar();
                }
            }
        }

    }


    // metodo para hacer una pausa y dar enter para continuar es en relacion con catch (NumberFormatException e)
    private void pausarYContinuar() {
        System.out.println("\nPresione ENTER para continuar...");
        teclado.nextLine(); // Espera que el usuario presione Enter
    }

    //metodo para hacer la limpieza de la pantalla
    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J"); // Secuencia ANSI (funciona en terminales modernas)
        System.out.flush();
        // Si no funciona, usamos líneas en blanco como respaldo:
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
    // validacion para el punto 4 del menu que cuando pregunte el año solo acepte numeros, ni letras ni caracteres especiales
    private int leerEnteroValido() {
        while (true) {
            String input = teclado.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("❌ No ingresó ningún valor. Por favor, ingrese un número: ");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("❌ Entrada inválida. Por favor, ingrese solo números (pueden ser negativos): ");
            }
        }
    }
}