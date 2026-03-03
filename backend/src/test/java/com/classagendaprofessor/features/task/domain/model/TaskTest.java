package com.classagendaprofessor.features.task.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    void throwsSecurityExceptionWhenUserIsNotTheOwner() {
        Long realOwnerId = 1L;
        Task myTask = new Task("Comprar leche", "Ir al súper", TaskPriority.MEDIUM, realOwnerId);

        Long hackerId = 99L;

        SecurityException exception = assertThrows(
                SecurityException.class,
                () -> myTask.validateIsOwnedBy(hackerId)
        );

        assertEquals("Acceso denegado: No eres el propietario de esta tarea.", exception.getMessage());
    }

    @Test
    void doesNotThrowExceptionWhenUserIsTheOwner() {
        Long realOwnerId = 1L;
        Task myTask = new Task("Mi tarea privada", "...", TaskPriority.LOW, realOwnerId);

        assertDoesNotThrow(() -> myTask.validateIsOwnedBy(realOwnerId));
    }

    @Test
    void taskIsCreatedWithPendingStatusByDefault() {
        Task newTask = new Task("Hacer los deberes", "Matemáticas", TaskPriority.HIGH, 1L);

        assertEquals(TaskStatus.PENDING, newTask.getStatus());
        assertNull(newTask.getId());
    }
}
