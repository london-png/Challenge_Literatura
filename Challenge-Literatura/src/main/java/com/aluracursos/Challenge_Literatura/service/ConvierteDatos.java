// com.aluracursos.Challenge_Literatura.service.ConvierteDatos.java

package com.aluracursos.Challenge_Literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvierteDatos implements IConvierteDatos {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
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
                throw new IllegalArgumentException("El JSON proporcionado es nulo o vacío");
            }
            return objectMapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir JSON a " + clase.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}