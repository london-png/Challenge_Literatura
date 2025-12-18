package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.model.Libro;
import com.aluracursos.Challenge_Literatura.service.LibroService;

import java.util.*;
import java.util.stream.Collectors;

public class ListViewBook {
    private final LibroService libroService;

    public ListViewBook(LibroService libroService) {
        this.libroService = libroService;
    }

    // 👇 MÉTODO MODIFICADO: Ahora agrupa por título y muestra "Versiones disponibles (X)"
    public void listarLibrosRegistrados() {
        List<Libro> libros = libroService.listarTodosLosLibros();
        long totaLibrosRegistados = libroService.contadorLibrosRegistrados();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        // ✅ CAMBIO PRINCIPAL: Agrupar libros por título
        Map<String, List<Libro>> librosPorTitulo = libros.stream()
                .collect(Collectors.groupingBy(Libro::getTitulo));

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("          📚 LIBROS REGISTRADOS EN LA BASE DE DATOS(%d libros)%n", totaLibrosRegistados);
        System.out.println("=".repeat(80));

        for (Map.Entry<String, List<Libro>> entrada : librosPorTitulo.entrySet()) {
            String titulo = entrada.getKey();
            List<Libro> versiones = entrada.getValue();

            // ✅ Muestra el título una vez y la cantidad de versiones
            System.out.printf("\n   📖 Título: %s%n", titulo);
            System.out.printf("      Versiones disponibles (%d):%n", versiones.size());

            // ✅ Muestra cada versión con detalles
            for (int i = 0; i < versiones.size(); i++) {
                Libro libro = versiones.get(i);
                System.out.printf("        %d. ID: %-6d | Consultado: %s%n",
                        i + 1,
                        libro.getId(),
                        libro.getFechaConsultaFormateada());

                List<Autor> autores = libroService.obtenerAutoresPorIds(libro.getAutorIds());
                if (!autores.isEmpty()) {
                    String autoresStr = autores.stream()
                            .map(autor -> String.format("%s (Nacido: %s | Muerto: %s)",
                                    autor.getNombre(),
                                    autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido",
                                    autor.getMuerte() != null ? autor.getMuerte() : "Desconocido"))
                            .collect(Collectors.joining(", "));
                    System.out.printf("           Autores: %s%n", autoresStr);
                }

                // Mostrar idioma(s)
                if (libro.getIdioma() != null && libro.getIdioma().length > 0) {
                    String idiomas = String.join(", ", libro.getIdioma());
                    System.out.printf("           Idioma(s): %s%n", idiomas);
                }

                // Mostrar descargas
                Integer descargas = libro.getDescargas();
                if (descargas != null) {
                    System.out.printf("           Descargas: %d%n", descargas);
                }

                System.out.println("           " + "-".repeat(50));
            }
        }
        System.out.println("-".repeat(80));
    }

    // 👇 Resto de métodos: sin cambios (pero incluidos para completar la clase)
    public void mostrarLibrosPorIdioma(List<Libro> libros, String idioma) {
        if (libros.isEmpty()) {
            System.out.println("\n❌ No se encontraron libros en el idioma: " + idioma);
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("   📚 LIBROS EN %s - %d libros%n", idioma.toUpperCase(), libros.size());
        System.out.println("=".repeat(80));

        for (Libro libro : libros) {
            System.out.printf("   • %s%n", libro.getTitulo());
            System.out.printf("     Consultado: %s%n", libro.getFechaConsultaFormateada());

            List<Autor> autores = libroService.obtenerAutoresPorIds(libro.getAutorIds());
            if (!autores.isEmpty()) {
                String autoresStr = autores.stream()
                        .map(autor -> autor.getNombre())
                        .collect(Collectors.joining(", "));
                System.out.printf("     Autores: %s%n", autoresStr);
            }
            System.out.println("     " + "-".repeat(50));
        }
        System.out.println("-".repeat(80));
    }

    public void mostrarMenuIdiomas(List<String> idiomas) {
        System.out.println("\n*** ELIJA UN IDIOMA ***");
        for (int i = 0; i < idiomas.size(); i++) {
            String codigo = idiomas.get(i);
            String nombre = obtenerNombreIdioma(codigo);
            System.out.printf("%d → %s (%s)%n", i + 1, nombre, codigo);
        }
        System.out.println("0 → Volver al menú principal");
    }

    private String obtenerNombreIdioma(String codigo) {
        return switch (codigo) {
            case "en" -> "inglés";
            case "es" -> "español";
            case "fr" -> "francés";
            case "de" -> "alemán";
            case "it" -> "italiano";
            case "pt" -> "portugués";
            case "fi" -> "finlandés";
            case "ru" -> "ruso";
            default -> "desconocido";
        };
    }
}