package com.aluracursos.Challenge_Literatura.util;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.service.AutorService;
import java.util.List;

// constructor que sirve para entregar una herramienta que necesita el AutoService
public class ListViewActors {
    private final AutorService autorService;

    //Sirve para inyectar (entregar) una dependencia crea un objeto de tipo ListViewActors
    public ListViewActors(AutorService autorService) {
        this.autorService= autorService;
    }

    //metodo para optener  la lista de todos los autores y cuanto cuantos autores hay al final
    public void ListarAutoresRegistrados() {
        List<Autor> autores = autorService.listarTodosLosAutores();
        long totalAutores = autorService.contadorAutoresRegistrados();

        //validacion si existe autores
        if (autores.isEmpty()) {
            System.out.println("No hay actores registrados.");
            return;
        }

        //imprime los autores registrados en la BD
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("           ACTORES REGISTRADOS EN LA BASE DE DATOS(%d actores)%n", totalAutores);
        System.out.println("=".repeat(80));

        for (Autor autor : autores) {
            System.out.printf("   ID: %-6d | Nombre: %s%n", autor.getId(), autor.getNombre(), autor.getNacimiento(), autor.getMuerte());

            //  muestra nacimiento y muerte
            String nacimientoStr = autor.getNacimiento() != null ? String.valueOf(autor.getNacimiento()) : "Desconocido";
            String muerteStr = autor.getMuerte() != null ? String.valueOf(autor.getMuerte()) : "Desconocido";

            System.out.printf("        Nacimiento: %s | Muerte: %s%n", nacimientoStr, muerteStr);

            System.out.println("-".repeat(80));

        }
    }
    //mrtodo para mostrar actores vivos en determinado rango
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
            System.out.printf("   • %s (Nacimiento: %s | Muerte: %s)%n", autor.getNombre(), nac, mue);
        }
        System.out.println("-".repeat(80));
    }
}



