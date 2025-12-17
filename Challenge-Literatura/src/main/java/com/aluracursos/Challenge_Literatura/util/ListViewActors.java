package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.model.Libro;
import com.aluracursos.Challenge_Literatura.service.AutorService;
import com.aluracursos.Challenge_Literatura.service.LibroService;

import java.util.List;
import java.util.stream.Collectors;

public class ListViewActors {
    private final AutorService autorService;

    public ListViewActors(AutorService autorService) {
        this.autorService= autorService;
    }
    public void ListarAutoresRegistrados() {
        List<Autor> autores = autorService.listarTodosLosAutores();
        long totalAutores = autorService.contadorAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("No hay actores registrados.");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("           ACTORES REGISTRADOS EN LA BASE DE DATOS(%d actores)%n", totalAutores);
        System.out.println("=".repeat(80));

        for (Autor autor : autores) {
            System.out.printf("   ID: %-6d | Nombre: %s%n", autor.getId(), autor.getNombre(), autor.getNacimiento(), autor.getMuerte());

            //  Corregido: Mostrar nombre, nacimiento y muerte
            String nacimientoStr = autor.getNacimiento() != null ? String.valueOf(autor.getNacimiento()) : "Desconocido";
            String muerteStr = autor.getMuerte() != null ? String.valueOf(autor.getMuerte()) : "Desconocido";

            System.out.printf("        Nacimiento: %s | Muerte: %s%n", nacimientoStr, muerteStr);

            System.out.println("-".repeat(80));

        }
    }
    //Nuevo método: Mostrar autores vivos en un rango
    public void mostrarAutoresVivosEnRango(List<Autor> autores, int inicio, int fin) {
        if (autores.isEmpty()) {
            System.out.println("\n No se encontraron autores vivos en la base de datos para el rango " + inicio + " - " + fin + ".");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("   📜 AUTORES VIVOS ENTRE %d Y %d (%d autores)%n", inicio, fin, autores.size());
        System.out.println("=".repeat(80));

        for (Autor autor : autores) {
            String nac = autor.getNacimiento() != null ? autor.getNacimiento().toString() : "Desconocido";
            String mue = autor.getMuerte() != null ? autor.getMuerte().toString() : "Desconocido";
            System.out.printf("   • %s (Nac: %s | Mue: %s)%n", autor.getNombre(), nac, mue);
        }
        System.out.println("-".repeat(80));
    }
}



