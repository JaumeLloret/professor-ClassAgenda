package com.classagendaprofessor.features.task.domain.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class Task {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Long ownerId;
    private final LocalDateTime createdAt;

    public Task(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        Long ownerId,
        LocalDateTime createdAt
    ) {
        validateTitle(title);
        validateOwner(ownerId);
        validateStatusAndPriority(status, priority);

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public Task(String title, String description, TaskPriority priority, Long ownerId) {
        this(
            null,
            title,
            description,
            TaskStatus.PENDING,
            priority,
            ownerId,
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
        }
        if (title.length() > 100) {
            throw new IllegalArgumentException("El título no puede superar los 100 caracteres.");
        }
    }

    private void validateOwner(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Toda tarea debe pertenecer a un usuario.");
        }
    }

    private void validateStatusAndPriority(TaskStatus status, TaskPriority priority) {
        if (status == null || priority == null) {
            throw new IllegalArgumentException("El estado y la prioridad son obligatorios.");
        }
    }

    public void validateIsOwnedBy(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("No se ha identificado al usuario que realiza la petición");
        }

        if(!this.ownerId.equals(userId)) {
            throw new SecurityException("Acceso denegado: No eres el propietario de esta tarea.");
        }
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }
}
