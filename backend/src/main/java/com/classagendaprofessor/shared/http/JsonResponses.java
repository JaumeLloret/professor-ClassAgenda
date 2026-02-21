package com.classagendaprofessor.shared.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class JsonResponses {
    private JsonResponses() {}

    public static void sendJson(HttpExchange httpExchange, int statusCode, String jsonBody) throws IOException {

        byte[] responseBytes = jsonBody.getBytes(StandardCharsets.UTF_8);

        httpExchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        httpExchange.sendResponseHeaders(statusCode, responseBytes.length);

        httpExchange.getResponseBody().write(responseBytes);
        httpExchange.close();
    }

}
