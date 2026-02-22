package com.classagendaprofessor.shared.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvLoaderTest {
    @Test
    void getRequiredThrowsExceptionWhenKeyDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> EnvLoader.getRequired("THIS_KEY_DOES_NOT_EXIST"));
    }
}
