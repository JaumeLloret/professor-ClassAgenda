package com.classagendaprofessor.features.task.data.repository;

import com.classagendaprofessor.features.task.data.local.dao.TaskDao;
import com.classagendaprofessor.features.task.domain.model.Task;
import com.classagendaprofessor.features.task.domain.model.TaskPriority;
import com.classagendaprofessor.features.task.domain.model.TaskStatus;
import com.classagendaprofessor.features.user.data.local.dao.UserDao;
import com.classagendaprofessor.features.user.data.repository.JdbcUserRepository;
import com.classagendaprofessor.features.user.domain.model.User;
import com.classagendaprofessor.shared.config.DbConfig;
import com.classagendaprofessor.shared.database.DbConnectionFactory;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTaskRepositoryIT {
    @Test
    void fullTaskCrudLifecycle() throws Exception {

        try { DbConfig.url(); } catch (Exception e) {
            Assumptions.abort("Saltando test: No hay BD configurada.");
        }

        DbConnectionFactory factory = new DbConnectionFactory();

        try (Connection connection = factory.getConnection()) {

            UserDao userDao = new UserDao(connection);
            TaskDao taskDao = new TaskDao(connection);

            JdbcUserRepository userRepository = new JdbcUserRepository(userDao);
            JdbcTaskRepository taskRepository = new JdbcTaskRepository(taskDao);

            String uniqueEmail = "owner_" + System.currentTimeMillis() + "@test.com";
            User owner = new User("Dueño", uniqueEmail);
            User savedOwner = userRepository.save(owner);

            Task newTask = new Task("Comprar pan", "Ir al súper", TaskPriority.HIGH, savedOwner.getId());
            Task savedTask = taskRepository.save(newTask);

            assertNotNull(savedTask.getId());
            assertEquals(TaskStatus.PENDING, savedTask.getStatus());

            List<Task> pendingTasks = taskRepository.findByOwnerIdAndStatus(savedOwner.getId(), TaskStatus.PENDING);
            assertEquals(1, pendingTasks.size());

            taskRepository.delete(savedTask.getId());
        }
    }
}
