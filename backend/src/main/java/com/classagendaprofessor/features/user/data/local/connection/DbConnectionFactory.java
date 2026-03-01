package com.classagendaprofessor.features.user.data.local.connection;

import com.classagendaprofessor.shared.config.DbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionFactory {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.url(),
                DbConfig.user(),
                DbConfig.password()
        );
    }
}
