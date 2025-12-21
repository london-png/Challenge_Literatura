// com.aluracursos.Challenge_Literatura.service.ConvierteDatos.java

package com.aluracursos.Challenge_Literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

//convierte texto JSON (que viene de internet) en objetos Java bien organizados

// esta clase es un componente que la guarda y permite inyectarla en lugares como el libros
@Component
public class ConvierteDatos implements IConvierteDatos {

    // herramienta de la librería Jackson (que usas para manejar JSON).Es el motor de traducción:
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override

    // <T> es generico funciona con cualquier tipo de objeto, se puede usar para RespuestaApi, DatosLibros,
    public <T> T obtenerDatos(String json, Class<T> clase) {

       // validar que el json no este vacio
        try {
            if (json == null || json.trim().isEmpty()) {

                // Manejo de JSON vacío para RespuestaApi
                if (clase == com.aluracursos.Challenge_Literatura.model.RespuestaApi.class) {
                    return clase.cast(
                            new com.aluracursos.Challenge_Literatura.model.RespuestaApi(
                                    0, null, null,
                                    new com.aluracursos.Challenge_Literatura.model.DatosLibros[0]
                            )
                    );
                }
                //convierte un json a un objeto java
                throw new IllegalArgumentException("El JSON proporcionado es nulo o vacío");
            }
            return objectMapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir JSON a " + clase.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}