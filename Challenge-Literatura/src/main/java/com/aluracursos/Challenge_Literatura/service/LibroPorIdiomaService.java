// com.aluracursos.Challenge_Literatura.service.LibroPorIdiomaService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibroPorIdiomaService {

    private final ConsumoApi consumoApi;
    private final ConvierteDatos convierteDatos;

    public LibroPorIdiomaService(ConsumoApi consumoApi, ConvierteDatos convierteDatos) {
        this.consumoApi = consumoApi;
        this.convierteDatos = convierteDatos;
    }

    public List<DatosLibros> buscarLibrosPorIdioma(String codigoIdioma) {
        if (codigoIdioma == null || codigoIdioma.trim().isEmpty()) {
            System.out.println("⚠️ Código de idioma inválido.");
            return new ArrayList<>();
        }

        // ✅ Corrección: Elimina los espacios al final de la URL
        String url = String.format(
                "https://gutendex.com/books?languages=%s",
                URLEncoder.encode(codigoIdioma, StandardCharsets.UTF_8)
        );

        try {
            // ✅ Paso 1: Obtener JSON crudo como String
            String json = consumoApi.obtenerDatos(url);

            // ✅ Paso 2: Deserializar a RespuestaApi
            RespuestaApi respuesta = convierteDatos.obtenerDatos(json, RespuestaApi.class);

            // ✅ Paso 3: Extraer los libros
            List<DatosLibros> libros = new ArrayList<>();
            if (respuesta.resultados() != null) {
                for (DatosLibros libro : respuesta.resultados()) {
                    libros.add(libro);
                }
            }

            return libros;

        } catch (Exception e) {
            System.err.println("❌ Error al obtener libros por idioma (" + codigoIdioma + "): " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String obtenerNombreIdioma(String codigo) {
        return switch (codigo) {
            case "en" -> "inglés";
            case "es" -> "español";
            case "fr" -> "francés";
            case "de" -> "alemán";
            case "it" -> "italiano";
            case "pt" -> "portugués";
            case "fi" -> "finlandés";
            case "ru" -> "ruso";
            default -> "desconocido";
        };
    }
}