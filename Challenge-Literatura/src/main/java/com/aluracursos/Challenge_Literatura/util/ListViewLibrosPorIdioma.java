// com.aluracursos.Challenge_Literatura.util.ListViewLibrosPorIdioma.java

package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.service.LibroPorIdiomaService;

import java.util.List;

public class ListViewLibrosPorIdioma {

    private final LibroPorIdiomaService libroPorIdiomaService;

    public ListViewLibrosPorIdioma(LibroPorIdiomaService libroPorIdiomaService) {
        this.libroPorIdiomaService = libroPorIdiomaService;
    }

    public void mostrarLibrosPorIdioma(List<DatosLibros> libros, String codigoIdioma) {
        String nombreIdioma = libroPorIdiomaService.obtenerNombreIdioma(codigoIdioma);

        if (libros.isEmpty()) {
            System.out.println("\n❌ No se encontraron libros en " + nombreIdioma + " (" + codigoIdioma + ").");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("   📚 LIBROS EN %s (%s) - %d libros%n", nombreIdioma.toUpperCase(), codigoIdioma, libros.size());
        System.out.println("=".repeat(80));

        for (DatosLibros libro : libros) {
            String titulo = libro.Titulo() != null ? libro.Titulo() : "Sin título";
            String autores = "Desconocido";
            if (libro.Autores() != null && libro.Autores().length > 0) {
                autores = String.join(", ", java.util.Arrays.stream(libro.Autores())
                        .map(a -> a.Nombre())
                        .toArray(String[]::new));
            }
            String idiomas = "Desconocido";
            if (libro.Idioma() != null && libro.Idioma().length > 0) {
                idiomas = String.join(", ", libro.Idioma());
            }
            System.out.printf("   • %s%n", titulo);
            System.out.printf("     Autores: %s%n", autores);
            System.out.printf("     Idioma(s): %s%n", idiomas);
            System.out.println("     " + "-".repeat(50));
        }
        System.out.println("-".repeat(80));
    }
}