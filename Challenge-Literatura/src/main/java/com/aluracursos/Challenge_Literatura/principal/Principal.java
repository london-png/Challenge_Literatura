package com.aluracursos.Challenge_Literatura.principal;

import com.aluracursos.Challenge_Literatura.model.Autor;
import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.model.Libro;
import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import com.aluracursos.Challenge_Literatura.service.ConsumoApi;
import com.aluracursos.Challenge_Literatura.service.LibroService;
import com.aluracursos.Challenge_Literatura.util.ListViewBook;
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
    private final LibroService libroService;
    private final Scanner teclado = new Scanner(System.in);
    private final ListViewBook listViewBook; // nueva insatancia del metodo que se creo en el paquete util clase listViewBook

    //constructor usado por Spring inyeccion de dependencias
    @Autowired
    public Principal(ConsumoApi consumoApi, LibroService libroService) {
        this.consumoApi = consumoApi;
        this.libroService = libroService;
        this.listViewBook = new ListViewBook(libroService); //para inicializar la vista
    }

    /*public Principal() {
        this.consumoApi = new ConsumoApi(); //funciona consumo api no requiere paramentros
    }*/

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
                case 1 -> {
                    System.out.println("Escriba el nombre del titulo que desea buscar");
                    String titulo = teclado.nextLine();

                    if (titulo != null && !titulo.trim().isEmpty()) {
                        libroService.buscarYMostrarLibros(titulo);

                    }else {
                        System.out.println("No ingreso ningun titulo. Regresando al menu");

                    }

                }

                case 2 -> {
                    listViewBook.listarLibrosRegistrados(); // llama el metodo en la vista
                    break;
                }

            }
        }
        teclado.close();
    }

}
