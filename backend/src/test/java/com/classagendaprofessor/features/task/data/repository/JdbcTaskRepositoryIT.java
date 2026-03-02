package com.classagendaprofessor.features.task.data.repository;

import com.classagendaprofessor.features.task.data.local.dao.TaskDao;
import com.classagendaprofessor.features.task.domain.model.Task;
import com.classagendaprofessor.features.task.domain.model.TaskPriority;
import com.classagendaprofessor.features.task.domain.model.TaskStatus;
import com.classagendaprofessor.features.user.data.local.dao.UserDao;
import com.classagendaprofessor.features.user.data.local.entity.UserEntity;
import com.classagendaprofessor.features.user.data.repository.JdbcUserRepository;
import com.classagendaprofessor.features.user.domain.model.User;
import com.classagendaprofessor.shared.config.DbConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JdbcTaskRepositoryIT {
    private JdbcTaskRepository taskRepository;
    private JdbcUserRepository userRepository;

    @BeforeEach
    void setUp() {
        try {
            DbConfig.url();
        } catch (Exception e) {
            Assumptions.abort("Saltando test de integración: No hay BD configurada en el entorno.");
        }

        userRepository = new JdbcUserRepository(new UserDao(new com.classagendaprofessor.features.user.data.local.connection.DbConnectionFactory()));
        taskRepository = new JdbcTaskRepository(new TaskDao(new com.classagendaprofessor.features.task.data.local.connection.DbConnectionFactory()));
    }

    @Test
    void savesTaskAndFiltersByOwnerAndStatus() {
        String tempEmail = "owner_" + System.currentTimeMillis() + "@test.com";
        User tempOwner = new User(null, "Task Owner", tempEmail, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        User savedOwner = userRepository.save(tempOwner);
        Long ownerId = savedOwner.getId();

        Task task1 = new Task("Aprender Java", "...", TaskPriority.HIGH, ownerId);
        Task savedTask1 = taskRepository.save(task1);

        List<Task> pendingTasks = taskRepository.findByOwnerIdAndStatus(ownerId, TaskStatus.PENDING);

        assertFalse(pendingTasks.isEmpty(), "Debería encontrar al menos 1 tarea pendiente");
        assertEquals(savedTask1.getId(), pendingTasks.getFirst().getId());
        assertEquals(ownerId, pendingTasks.getFirst().getOwnerId());
    }
}
