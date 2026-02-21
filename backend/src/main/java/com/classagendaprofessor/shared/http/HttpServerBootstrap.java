package com.classagendaprofessor.shared.http;

import com.classagendaprofessor.features.example.presentation.router.ExampleRouter;
import com.classagendaprofessor.shared.config.ServerConfig;
import com.classagendaprofessor.shared.http.handlers.HealthHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public final class HttpServerBootstrap {
    public void start() throws Exception {
        HttpServer httpServer = startAndReturnServer();
        int serverPort = httpServer.getAddress().getPort();
        System.out.println("ClassAgenda API funcionando en http://localhost:" + serverPort);
    }

    public HttpServer startAndReturnServer() throws Exception {

        int configuredPort = ServerConfig.port();
        InetSocketAddress serverAddress = new InetSocketAddress(configuredPort);

        HttpServer httpServer = HttpServer.create(serverAddress, 0);

        httpServer.createContext("/health", new HealthHandler());
        ExampleRouter.registerRoutes(httpServer);

        httpServer.start();

        return httpServer;
    }
}
