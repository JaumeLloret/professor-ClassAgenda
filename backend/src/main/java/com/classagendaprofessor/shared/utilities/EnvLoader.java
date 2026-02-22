package com.classagendaprofessor.shared.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class EnvLoader {
    private static final String ENV_FILE_NAME = ".env";
    private static final Map<String, String> ENV_VALUES = new HashMap<>();

    static {
        loadEnvFile();
    }

    private EnvLoader() {}

    private static void loadEnvFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(ENV_FILE_NAME))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {

                currentLine = currentLine.trim();

                if (currentLine.isEmpty() || currentLine.startsWith("#")) {
                    continue;
                }

                String[] keyAndValue = currentLine.split("=", 2);

                if (keyAndValue.length == 2) {
                    String key = keyAndValue[0].trim();
                    String value = keyAndValue[1].trim();
                    ENV_VALUES.put(key, value);
                }
            }

        } catch (IOException exception) {
            throw new IllegalStateException("No se puede leer el archivo .env. ¿Existe y está en la raíz del backend?", exception);
        }

    }

    public static String getRequired(String key) {
        String value = ENV_VALUES.get(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta la clave en el .env: " + key);
        }

        return value;
    }
}
