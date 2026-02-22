package com.classagendaprofessor.features.example.presentation.handlers;

import com.classagendaprofessor.shared.http.JsonResponses;
import com.classagendaprofessor.shared.http.ResponseContract;
import com.classagendaprofessor.shared.utilities.JsonEscaper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public final class ExampleIntroHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String httpMethod = exchange.getRequestMethod();

            switch (httpMethod) {
                case "GET" -> sendOkResponse(exchange, "GET ok");
                case "POST" -> sendOkResponse(exchange, "POST ok");
                case "PUT" -> sendOkResponse(exchange, "PUT ok");
                case "PATCH" -> sendOkResponse(exchange, "PATCH ok");
                case "DELETE" -> sendOkResponse(exchange, "DELETE ok");
                default -> sendMethodNotAllowed(exchange);
            }
        } catch (Exception exception) {
            sendServerError(exchange, exception.getMessage());
        }
    }

    private void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "GET, POST, PUT, PATCH, DELETE");

        String jsonBody = "{" +
                "\"status\":\"error\"," +
                "\"message\":\"Method not allowed\"" +
                "}";

        JsonResponses.sendJson(exchange, 405, jsonBody);
    }

    private void sendServerError(HttpExchange exchange, String errorDetails) throws IOException {
        String responseJson = ResponseContract.errorJson("Internal server error", errorDetails);
        JsonResponses.sendJson(exchange, 500, responseJson);
    }

    private void sendOkResponse(HttpExchange exchange, String message) throws IOException {
        String receivedBody = readRequestBody(exchange);

        String dataJson =
                "{"
                        + "\"endpoint\":\"example/intro\","
                        + "\"method\":\"" + exchange.getRequestMethod() + "\","
                        + "\"message\":\"" + JsonEscaper.escape(message) + "\","
                        + "\"receivedBody\":" + toNullableJsonString(receivedBody)
                        + "}";

        String responseJson = ResponseContract.okJson(dataJson);
        JsonResponses.sendJson(exchange, 200, responseJson);
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream requestBodyStream = exchange.getRequestBody();
        if(requestBodyStream == null) return null;

        byte[] bodyBytes = requestBodyStream.readAllBytes();
        if(bodyBytes.length == 0) return null;

        return new String(bodyBytes, StandardCharsets.UTF_8);
    }

    private String toNullableJsonString(String message) {
        if (message == null) return "null";
        return "\"" + JsonEscaper.escape(message) + "\"";
    }
}
