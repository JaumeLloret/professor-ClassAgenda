package com.classagendaprofessor.shared.http.handlers;

import com.classagendaprofessor.shared.http.ResponseContract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HealthHandlerTest {
    @Test
    void healthResponseContractContainsMandatoryFields() {
        String dataJson = "{\"endpoint\":\"health\",\"message\":\"Service is healthy\"}";
        String responseJson = ResponseContract.okJson(dataJson);

        assertTrue(responseJson.contains("\"status\":\"ok\""));
        assertTrue(responseJson.contains("\"service\":\"ClassAgenda\""));
        assertTrue(responseJson.contains("\"timestamp\""));
        assertTrue(responseJson.contains("\"data\""));
        assertTrue(responseJson.contains("\"endpoint\":\"health\""));
    }
}
