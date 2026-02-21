package com.classagendaprofessor.shared.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseContractTest {
    @Test
    void okJsonContainsMandatoryFields() {
        String json = ResponseContract.okJson("{\"hello\":\"world\"}");

        assertTrue(json.contains("\"status\":\"ok\""));
        assertTrue(json.contains("\"service\":\"ClassAgenda\""));
        assertTrue(json.contains("\"data\""));
        assertTrue(json.contains("\"timestamp\""));
    }

    @Test
    void errorJsonContainsMandatoryFields() {
        String json = ResponseContract.errorJson("Boom", "Details");

        assertTrue(json.contains("\"status\":\"error\""));
        assertTrue(json.contains("\"service\":\"ClassAgenda\""));
        assertTrue(json.contains("\"error\""));
        assertTrue(json.contains("\"timestamp\""));
    }
}
