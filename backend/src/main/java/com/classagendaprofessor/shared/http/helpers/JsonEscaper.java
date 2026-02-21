package com.classagendaprofessor.shared.http.helpers;

public final class JsonEscaper {
    private JsonEscaper() {}

    public static String escape(String rawText) {
        if (rawText == null) return "";
        return rawText
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
