package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.model.Libro;
import com.aluracursos.Challenge_Literatura.service.LibroService;

import java.util.List;
import java.util.stream.Collectors;

public class ListViewBook {
    private final LibroService libroService;

    public ListViewBook(LibroService libroService) {
        this.libroService= libroService;
    }
    public void listarLibrosRegistrados() {
        List<Libro> libros = libroService.listarTodosLosLibros();
        long totaLibrosRegistados = libroService.contadorLibrosRegistrados();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("          📚 LIBROS REGISTRADOS EN LA BASE DE DATOS(%d libros)%n", totaLibrosRegistados);
        System.out.println("=".repeat(80));

        for (Libro libro : libros) {
            System.out.printf("   ID: %-6d | Título: %s%n", libro.getId(), libro.getTitulo());

            // 👇 Corregido: Mostrar nombre, nacimiento y muerte
            List<Autor> autores = libroService.obtenerAutoresPorIds(libro.getAutorIds());
            if (!autores.isEmpty()) {
                String autoresStr = autores.stream()
                        .map(autor -> String.format("%s (Nacido: %s | Muerto: %s)",
                                autor.getNombre(),
                                autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido",
                                autor.getMuerte() != null ? autor.getMuerte() : "Desconocido"))
                        .collect(Collectors.joining(", "));
                System.out.printf("        Autores: %s%n", autoresStr);
            }

            System.out.println("-".repeat(80));
        }
    }
}
