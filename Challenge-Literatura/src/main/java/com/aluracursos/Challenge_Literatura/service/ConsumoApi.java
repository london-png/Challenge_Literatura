package com.aluracursos.Challenge_Literatura.service;

import com.aluracursos.Challenge_Literatura.model.RespuestaApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoApi {
    private final ObjectMapper objectMapper = new ObjectMapper(); //declaración de un campo de instancia constante

    public RespuestaApi obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException("Error de red al consumir la API: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción
            throw new RuntimeException("Error al deserializar la respuesta JSON: " + e.getMessage(), e);
        }
        try {
            return objectMapper.readValue(response.body(), RespuestaApi.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar la respuesta JSON: " + e.getMessage(), e);
        }
    }
}

