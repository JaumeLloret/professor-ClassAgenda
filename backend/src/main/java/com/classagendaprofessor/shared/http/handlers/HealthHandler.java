package com.classagendaprofessor.shared.http.handlers;

import com.classagendaprofessor.shared.http.JsonResponses;
import com.classagendaprofessor.shared.http.ResponseContract;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public final class HealthHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String dataJson =
                "{"
                        + "\"endpoint\":\"health\","
                        + "\"message\":\"Service is healthy\""
                        + "}";

        String responseJson = ResponseContract.okJson(dataJson);

        JsonResponses.sendJson(exchange, 200, responseJson);
    }
}
