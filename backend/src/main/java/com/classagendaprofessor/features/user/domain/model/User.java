package com.classagendaprofessor.features.user.domain.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class User {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public User(Long id, String name, String email, LocalDateTime createdAt) {
        validateFullName(name);
        validateEmail(email);
        validateCreatedAt(createdAt);

        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public User(String name, String email) {
        this(null, name, email, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    private void validateFullName(String nameToValidate) {
        if (nameToValidate == null || nameToValidate.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nameToValidate.length() > 80) {
            throw new IllegalArgumentException("El nombre no puede superar los 80 caracteres.");
        }
    }

    private void validateEmail(String emailToValidate) {
        if (emailToValidate == null || emailToValidate.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío.");
        }
        if (emailToValidate.length() > 255) {
            throw new IllegalArgumentException("El correo electrónico no puede superar los 255 caracteres.");
        }
        if (!emailToValidate.contains("@")) {
            throw new IllegalArgumentException("El correo electrónico debe tener un formato válido (contener '@').");
        }
    }

    private void validateCreatedAt(LocalDateTime dateToValidate) {
        if (dateToValidate == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
