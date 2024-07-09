package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ConsumoAPI {
    private HttpClient client;
    private String url;
    private HttpRequest request;
    HttpResponse<String> response;

    public ConsumoAPI() {
        client = HttpClient.newHttpClient();
        url = "https://gutendex.com/books";
    }

    private HttpResponse<String> enviarConsulta(HttpRequest request) {
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<String> obtenerDatos(){
        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        response = enviarConsulta(request);
        return Optional.ofNullable(response.body());
        }

    public Optional<String> buscarDatos(String titulo){
        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/?search=" + titulo.replace(" ", "%20")))
                .build();
        response = enviarConsulta(request);
        return Optional.ofNullable(response.body());
    }
}
