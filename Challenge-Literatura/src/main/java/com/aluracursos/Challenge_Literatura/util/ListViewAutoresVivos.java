// com.aluracursos.Challenge_Literatura.util.ListViewAutoresVivos.java

package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.service.AutorService;

import java.util.List;
import java.util.stream.Collectors;

public class ListViewAutoresVivos {
    private final AutorService autorService;

    public ListViewAutoresVivos(AutorService autorService) {
        this.autorService = autorService;
    }

    public void mostrarAutoresVivosEnRango(List<Autor> autores, int inicio, int fin) {
        if (autores.isEmpty()) {
            System.out.println("\n❌ No se encontraron autores vivos en la base de datos para el rango " + inicio + " - " + fin + ".");
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