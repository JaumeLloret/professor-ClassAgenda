package com.classagendaprofessor.shared.config;

import com.classagendaprofessor.shared.utilities.EnvLoader;

public final class DbConfig {
    private DbConfig() {}

    public static String url() {
        return EnvLoader.getRequired("CLASSAGENDA_DB_URL");
    }

    public static String user() {
        return EnvLoader.getRequired("CLASSAGENDA_DB_USER");
    }

    public static String password() {
        return EnvLoader.getRequired("CLASSAGENDA_DB_PASSWORD");
    }
}
