// com.aluracursos.Challenge_Literatura.service.AutorVivoApiService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.DatosAutor;
import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Service
public class AutorVivoApiService {

    //inyeccion de dependencias
    private final ConsumoApi consumoApi; //hace llamadas al HTTP
    private final ConvierteDatos convierteDatos; //convierte el Json que recibio en objetos java

    public AutorVivoApiService(ConsumoApi consumoApi, ConvierteDatos convierteDatos) {
        this.consumoApi = consumoApi;
        this.convierteDatos = convierteDatos;
    }


    //se construye la url de la api
    public Set<DatosAutor> buscarAutoresVivos(int inicio, int fin) {
        String url = String.format(
                "https://gutendex.com/books?author_year_start=%s&author_year_end=%s",
                URLEncoder.encode(String.valueOf(inicio), StandardCharsets.UTF_8),// URLEncoder asegura que los números se envíen correctamente
                URLEncoder.encode(String.valueOf(fin), StandardCharsets.UTF_8)
        );

        try {
            //hace la llamada al Api para obtener el json
            String json = consumoApi.obtenerDatos(url);

            // convierte el json en un objeto java
            RespuestaApi respuesta = convierteDatos.obtenerDatos(json, RespuestaApi.class);

            //usa St y no lista para no traer duplicados
            Set<DatosAutor> autoresUnicos = new HashSet<>();
            if (respuesta.resultados() != null) {

                //recorre todos los libros y sus autores
                for (DatosLibros libro : respuesta.resultados()) {
                    if (libro.Autores() != null) {
                        for (DatosAutor autor : libro.Autores()) {
                            if (estaVivoEnRango(autor, inicio, fin)) {
                                autoresUnicos.add(autor);
                            }
                        }
                    }
                }
            }

            // devuelve los autores unicos sin repetirlos
            return autoresUnicos;

        } catch (Exception e) {
            System.err.println("Error al obtener autores vivos: " + e.getMessage());
            return new HashSet<>();
        }
    }

    // verifica si en algun momento un autor estuvo vivo dentro de ese rango
    private boolean estaVivoEnRango(DatosAutor autor, int inicio, int fin) {
        Integer nacimiento = autor.Nacimiento();
        Integer muerte = autor.Muerte();

        if (nacimiento == null) {
            return false;
        }

        int muerteReal = (muerte != null) ? muerte : Integer.MAX_VALUE;

        return nacimiento <= fin && muerteReal >= inicio;
    }
}