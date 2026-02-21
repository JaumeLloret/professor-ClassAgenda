package com.classagendaprofessor.shared.http;

import com.classagendaprofessor.shared.http.helpers.JsonEscaper;

import java.time.Instant;

public final class ResponseContract {
    private static final String SERVICE_NAME = "ClassAgenda";

    private ResponseContract() {}

    public static String okJson(String dataJsonObject) {

        String safeDataJsonObject = (dataJsonObject == null || dataJsonObject.isBlank())
                ? "null"
                : dataJsonObject;

        return "{"
                + "\"status\":\"ok\","
                + "\"service\":\"" + SERVICE_NAME + "\","
                + "\"timestamp\":\"" + Instant.now() + "\","
                + "\"data\":" + safeDataJsonObject
                + "}";
    }

    public static String errorJson(String message, String details) {

        String safeMessage = JsonEscaper.escape(message);
        String safeDetails = JsonEscaper.escape(details);

        return "{"
                + "\"status\":\"error\","
                + "\"service\":\"" + SERVICE_NAME + "\","
                + "\"timestamp\":\"" + Instant.now() + "\","
                + "\"error\":{"
                        + "\"message\":\"" + safeMessage + "\","
                        + "\"details\":" + toNullableJsonString(safeDetails)
                        + "}"
                + "}";
    }

    private static String toNullableJsonString(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) return "null";
        return "\"" + rawValue + "\"";
    }
}
