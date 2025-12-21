// com.aluracursos.Challenge_Literatura.service.LibroPorIdiomaService.java

package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.DatosLibros;
import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service // para indicar que es un servicio
public class LibroPorIdiomaService {

    // Inyección de dependencias
    private final ConsumoApi consumoApi;
    private final ConvierteDatos convierteDatos;

    //Buscar libros disponibles en un idioma específico
    public LibroPorIdiomaService(ConsumoApi consumoApi, ConvierteDatos convierteDatos) {
        this.consumoApi = consumoApi;
        this.convierteDatos = convierteDatos;
    }

    //validar codigo de l idioma
    public List<DatosLibros> buscarLibrosPorIdioma(String codigoIdioma) {
        if (codigoIdioma == null || codigoIdioma.trim().isEmpty()) {
            System.out.println("⚠️ Código de idioma inválido.");
            return new ArrayList<>();
        }

        // Elimina los espacios al final de la URL
        String url = String.format(
                "https://gutendex.com/books?languages=%s",
                URLEncoder.encode(codigoIdioma, StandardCharsets.UTF_8)
        );

        try {
            //Obtener JSON hace la llamada y procesa la respuesta
            String json = consumoApi.obtenerDatos(url);

            //Deserializar a RespuestaApi
            RespuestaApi respuesta = convierteDatos.obtenerDatos(json, RespuestaApi.class);

            //Extraer los libros
            List<DatosLibros> libros = new ArrayList<>();
            if (respuesta.resultados() != null) {
                for (DatosLibros libro : respuesta.resultados()) {
                    libros.add(libro);
                }
            }

            return libros;

        //manejo de errores
        } catch (Exception e) {
            System.err.println("❌ Error al obtener libros por idioma (" + codigoIdioma + "): " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Convierte codigos cortos en nombres legibles
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