package com.classagendaprofessor.features.user.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void userIsCreatedCorrectlyFromDatabase() {
        LocalDateTime date = LocalDateTime.of(2023, 4, 12, 12, 34);
        User user = new User(1L, "María García", "maria@ejemplo.com", date);

        assertEquals(1L, user.getId());
        assertEquals("María García", user.getName());
        assertEquals("maria@ejemplo.com", user.getEmail());
        assertEquals(date, user.getCreatedAt());
    }

    @Test
    void newUserIsCreatedCorrectlyWithoutId() {
        User user = new User("Julián Gracía", "julian@ejemplo.com");

        assertNull(user.getId());
        assertEquals("Julián Gracía", user.getName());
        assertNotNull(user.getCreatedAt());
    }


    @Test
    void throwsExceptionWhenNameIsTooLong() {
        String superLongName = "A".repeat(81);

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(superLongName, "test@test.com")
        );
    }

    @Test
    void throwsExceptionWhenEmailIsInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new User("Juan", "correosinarroba.com")
        );
    }

}
