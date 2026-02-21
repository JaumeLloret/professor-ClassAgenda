package com.classagendaprofessor.shared.config;

public final class ServerConfig {
    private static final int DEFAULT_PORT = 8080;

    private ServerConfig() {}

    public static int port() {
        String rawPortFromEnv = System.getenv("CLASSAGENDA_PORT");
        return parseOrDefaultPort(rawPortFromEnv);
    }

    static int parseOrDefaultPort(String rawPort) {

        if (rawPort == null || rawPort.isBlank()) {
            return DEFAULT_PORT;
        }

        int parsedPort = tryParse(rawPort);

        if (!isValidPort(parsedPort)) {
            return DEFAULT_PORT;
        }

        return parsedPort;
    }

    private static int tryParse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }
}
