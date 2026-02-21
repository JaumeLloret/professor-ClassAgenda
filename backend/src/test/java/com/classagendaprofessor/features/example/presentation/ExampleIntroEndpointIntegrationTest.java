package com.classagendaprofessor.features.example.presentation;

import com.classagendaprofessor.shared.http.HttpServerBootstrap;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleIntroEndpointIntegrationTest {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @Test
    void getExampleIntroReturnsJsonAnd200() throws Exception {
        System.setProperty("CLASSAGENDA_PORT", "0");
        HttpServerBootstrap bootstrap = new HttpServerBootstrap();

        HttpServer httpServer = bootstrap.startAndReturnServer();
        int realPort = httpServer.getAddress().getPort();

        try {
            URI endpointUri = URI.create("http://localhost:" + realPort + "/example/intro");
            HttpRequest httpRequest = HttpRequest.newBuilder(endpointUri).GET().build();

            HttpResponse<String> httpResponse = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, httpResponse.statusCode());
            assertTrue(httpResponse.body().contains("\"status\":\"ok\""));
            assertTrue(httpResponse.body().contains("\"method\":\"GET\""));

        } finally {
            httpServer.stop(0);
        }
    }
}
