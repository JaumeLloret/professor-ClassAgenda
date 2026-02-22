package com.classagendaprofessor.features.example.data.local.connection;

import java.sql.Connection;

public final class DbSmokeTest {
    public static void main(String[] args) throws Exception {
        DbConnectionFactory connectionFactory = new DbConnectionFactory();

        try (Connection connection = connectionFactory.getConnection()) {
            String databaseName = connection.getMetaData().getDatabaseProductName();
            System.out.println("✅ DB connected: " + databaseName);
        }
    }
}
