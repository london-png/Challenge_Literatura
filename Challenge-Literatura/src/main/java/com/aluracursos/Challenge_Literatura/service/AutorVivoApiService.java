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

    private final ConsumoApi consumoApi;
    private final ConvierteDatos convierteDatos;

    public AutorVivoApiService(ConsumoApi consumoApi, ConvierteDatos convierteDatos) {
        this.consumoApi = consumoApi;
        this.convierteDatos = convierteDatos;
    }

    public Set<DatosAutor> buscarAutoresVivos(int inicio, int fin) {
        String url = String.format(
                "https://gutendex.com/books?author_year_start=%s&author_year_end=%s",
                URLEncoder.encode(String.valueOf(inicio), StandardCharsets.UTF_8),
                URLEncoder.encode(String.valueOf(fin), StandardCharsets.UTF_8)
        );

        try {
            // ✅ Paso 1: Obtener JSON crudo como String
            String json = consumoApi.obtenerDatos(url);

            // ✅ Paso 2: Deserializar a RespuestaApi
            RespuestaApi respuesta = convierteDatos.obtenerDatos(json, RespuestaApi.class);

            Set<DatosAutor> autoresUnicos = new HashSet<>();
            if (respuesta.resultados() != null) {
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

            return autoresUnicos;

        } catch (Exception e) {
            System.err.println("Error al obtener autores vivos: " + e.getMessage());
            return new HashSet<>();
        }
    }

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