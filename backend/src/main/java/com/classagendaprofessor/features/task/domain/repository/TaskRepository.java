package com.classagendaprofessor.features.task.domain.repository;

import com.classagendaprofessor.features.task.domain.model.Task;
import com.classagendaprofessor.features.task.domain.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task newTask);
    Optional<Task> findById(Long id);
    List<Task> findByOwnerId(long id);
    List<Task> findByOwnerIdAndStatus(long ownerId, TaskStatus status);
    void delete(Long id);
}
