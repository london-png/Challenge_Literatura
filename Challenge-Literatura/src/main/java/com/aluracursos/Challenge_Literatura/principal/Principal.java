package com.aluracursos.Challenge_Literatura.principal;

import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import com.aluracursos.Challenge_Literatura.service.ConsumoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import com.aluracursos.Challenge_Literatura.util.FormateadorDeTexto;


@Component //esto le indica a Spring que maneje esa clase
public class Principal {
    private final ConsumoApi consumoApi;
    private final Scanner teclado = new Scanner(System.in);

    //constructor usado por Spring inyeccion de dependencias
    @Autowired
    public Principal(ConsumoApi consumoApi) {
        this.consumoApi = consumoApi;
    }

    public Principal() {
        this.consumoApi = new ConsumoApi(); //funciona consumo api no requiere paramentros
    }

   /* private Scanner teclado = new Scanner(System.in);
    //private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE ="https://gutendex.com/books/?page=2";*/

    // creamos un metodo
    public  void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    *******************************************************
                    ***        ELIJA UNA  DE LAS OPCIONES DEL MENU      ***
                    ***   1-Buscar libro por titulo                     ***
                    ***   2-Listar libros registrados                   ***
                    ***   3-Listar autores registrados                  ***
                    ***   4-Listar autores vivos en un determinado año  ***
                    ***   5-Listar libros por idioma                    ***
                    ***   0-Salir                                       ***
                    *******************************************************
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            /*var json = consumoApi.obtenerDatos(URL_BASE);
            System.out.println("===respuesta de laapi == ");
            System.out.println(json);*/

            //creamoa el switch para tomar la opcion que dijito el usuario
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;


            }
        }
        teclado.close();
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el nombre del titulo que desea buscar");
        String nombreDelLibro = teclado.nextLine();

        if (nombreDelLibro == null || nombreDelLibro.trim().isEmpty()) {
            System.out.println("No ingreso ningun titulo. Regresando al menu");
            return;
        }

        //usamos la Api
        String URL_BASE = "https://gutendex.com/books/?search=" + URLEncoder.encode(nombreDelLibro.trim(), StandardCharsets.UTF_8);
        System.out.println(URL_BASE);

        try {
            RespuestaApi respuesta = consumoApi.obtenerDatos(URL_BASE);
            if (respuesta.resultados() == null || respuesta.resultados().length == 0) {
                System.out.println("No se encontraron libros con ese titulo");
                return;
            }

            //  agrupamos los libros por titulo
            Map<String, List<DatosLibros>> libosPorTitulo = new HashMap<>();
            for (DatosLibros lIbros : respuesta.resultados()) {
                String titulo = lIbros.titulo();
                libosPorTitulo.computeIfAbsent(titulo, k -> new ArrayList<>()).add(lIbros);
            }

            //  imprimir resultados agrupados
            System.out.println("\n Resultados encontrados");
            for (Map.Entry<String, List<DatosLibros>> entrada : libosPorTitulo.entrySet()) {
                String titulo = entrada.getKey();
                List<DatosLibros> versiones = entrada.getValue();

                System.out.println("\n Titulo: " + titulo);
                System.out.println(" Versiones disponibles (" + versiones.size() + "):");
                System.out.println();

                for (int i = 0; i < versiones.size(); i++) {
                    DatosLibros libro = versiones.get(i);

                    // Autores
                    String autores = "";
                    if (libro.Autores() != null && libro.Autores().length > 0) {
                        autores = Arrays.stream(libro.Autores())
                                .map(autor -> String.format("%s (Nacimiento: %s | Muerte: %s)",
                                        autor.Nombre(),
                                        autor.Nacimiento() != null ? autor.Nacimiento() : "desonocido",
                                        autor.Muerte() != null ? autor.Muerte() : "desconocido"))
                                .collect(Collectors.joining(", "));


                    } else {
                        autores = "Desconocido";
                    }

                    // Idiomas
                    String idiomas = "";
                    if (libro.Idioma() != null && libro.Idioma().length > 0) {
                        idiomas = String.join(", ", libro.Idioma());
                    } else {
                        idiomas = "Desconocido";
                    }

                    // descargas
                    Integer descargas = libro.Descargas();
                    if (descargas == null) {
                        descargas = 0;
                    }

                    // resumen
                    String resumen = "";
                    if (libro.Resumen() != null && libro.Resumen().length > 0) {
                        resumen = FormateadorDeTexto.formatear(libro.Resumen()[0], 80);
                    } else {
                        resumen = "No tiene resumen";
                    }

                    //imprimir
                    System.out.printf("    %d. ID: %d\n", i + 1, libro.Id());
                    System.out.printf("       Autores: %s\n", autores);
                    System.out.printf("       Idioma(s): %s\n", idiomas);
                    System.out.printf("       Descargas: %d\n", descargas);
                    System.out.println("       Resumen:");
                    System.out.println("      " + resumen); // Sangría para el resumen
                    System.out.println("       ----------------------------------------");
                    System.out.println();

                }
            }

        } catch (Exception e) {
            System.out.println("Error en el proceso de busquedad: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metodo para formatear texto

}