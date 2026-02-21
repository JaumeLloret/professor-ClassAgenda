package com.classagendaprofessor.shared.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerConfigTest {
    @Test
    void returnsDefaultPortWhenValueIsMissingOrBlank() {
        assertEquals(8080, ServerConfig.parseOrDefaultPort(null));
        assertEquals(8080, ServerConfig.parseOrDefaultPort("   "));
    }

    @Test
    void returnsDefaultPortWhenValueIsNotValidNumber() {
        assertEquals(8080, ServerConfig.parseOrDefaultPort("hola"));
        assertEquals(8080, ServerConfig.parseOrDefaultPort("70000"));
    }

    @Test
    void returnsConfiguredPortWhenValueIsValid() {
        assertEquals(3000, ServerConfig.parseOrDefaultPort("3000"));
    }
}
