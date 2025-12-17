// com.aluracursos.Challenge_Literatura.service.ConsumoApi.java

package com.aluracursos.Challenge_Literatura.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoApi {

    public String obtenerDatos(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL no puede ser nula o vacía");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException("Error de red al consumir la API (" + url + "): " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La solicitud fue interrumpida (" + url + "): " + e.getMessage(), e);
        }

        String responseBody = response.body();

        // Depuración: Muestra los primeros 500 caracteres del JSON recibido
        if (responseBody != null) {
            System.out.println("🔍 JSON recibido (primeros 500 caracteres):");
            System.out.println(responseBody.substring(0, Math.min(500, responseBody.length())));
            System.out.println("----- FIN DEL JSON -----");
        }

        return responseBody;
    }
}