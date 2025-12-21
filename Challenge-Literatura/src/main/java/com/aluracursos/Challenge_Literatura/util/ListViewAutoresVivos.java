// com.aluracursos.Challenge_Literatura.util.ListViewAutoresVivos.java

package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.service.AutorService;
import java.util.List;

//la funcion es mostrar al usuario, de forma ordenada y visual, los autores que estuvieron vivos entre los años dados.
public class ListViewAutoresVivos {
    private final AutorService autorService;

    //constructor con inyeccion de depencia
    public ListViewAutoresVivos(AutorService autorService) {
        this.autorService = autorService;
    }

    public void mostrarAutoresVivosEnRango(List<Autor> autores, int inicio, int fin) {

        //manejo de lista vacia
        if (autores.isEmpty()) {
            System.out.println("\n❌ No se encontraron autores vivos en la base de datos para el rango " + inicio + " - " + fin + ".");
            return;
        }

        // imprime titulo
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("   AUTORES VIVOS ENTRE %d Y %d (%d autores)%n", inicio, fin, autores.size());
        System.out.println("=".repeat(80));

        //reccore y muestra cada autor del bucle
        for (Autor autor : autores) {
            String nac = autor.getNacimiento() != null ? autor.getNacimiento().toString() : "Desconocido";
            String mue = autor.getMuerte() != null ? autor.getMuerte().toString() : "Desconocido";
            System.out.printf("   • %s (Nac: %s | Mue: %s)%n", autor.getNombre(), nac, mue);
        }
        System.out.println("-".repeat(80));
    }
}