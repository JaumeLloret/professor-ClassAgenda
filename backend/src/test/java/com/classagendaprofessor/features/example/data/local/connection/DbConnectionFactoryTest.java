package com.classagendaprofessor.features.example.data.local.connection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DbConnectionFactoryTest {
    @Test
    public void testConnection() {
        DbConnectionFactory dbConnectionFactory = new DbConnectionFactory();
        assertNotNull(dbConnectionFactory);
    }
}
