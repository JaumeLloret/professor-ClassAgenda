package com.classagendaprofessor.features.user.data.repository;

import com.classagendaprofessor.features.user.data.local.connection.DbConnectionFactory;
import com.classagendaprofessor.features.user.data.local.dao.UserDao;
import com.classagendaprofessor.features.user.domain.model.User;
import com.classagendaprofessor.shared.config.DbConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcUserRepositoryIT {
    private JdbcUserRepository userRepository;

    @BeforeEach
    void setUp() {
        try {
            DbConfig.url();
        } catch (Exception e) {
            Assumptions.abort("Saltando test de integración: No hay configuración de base de datos (.env) disponible.");
        }

        DbConnectionFactory connectionFactory = new DbConnectionFactory();
        UserDao userDao = new UserDao(connectionFactory);
        userRepository = new JdbcUserRepository(userDao);
    }

    @Test
    void savesNewUserAndFindsItByEmail() {
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@ejemplo.com";
        User userToSave = new User("Prueba Integración", uniqueEmail);

        User savedUser = userRepository.save(userToSave);

        assertNotNull(savedUser.getId(), "El usuario guardado debería tener un ID generado por la BD");
        assertEquals("Prueba Integración", savedUser.getName());
        assertEquals(uniqueEmail, savedUser.getEmail());

        Optional<User> foundUserOpt = userRepository.findByEmail(uniqueEmail);
        assertTrue(foundUserOpt.isPresent(), "Deberíamos encontrar al usuario recién guardado");

        User foundUser = foundUserOpt.get();
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(
                savedUser.getCreatedAt().truncatedTo(ChronoUnit.SECONDS),
                foundUser.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)
        );
    }
}
