package com.classagendaprofessor.features.task.data.mapper;

import com.classagendaprofessor.features.task.data.local.entity.TaskEntity;
import com.classagendaprofessor.features.task.domain.model.Task;
import com.classagendaprofessor.features.task.domain.model.TaskPriority;
import com.classagendaprofessor.features.task.domain.model.TaskStatus;

public final class TaskMapper {
    private TaskMapper() {}

    public static TaskEntity toEntity(Task task) {
        if (task == null) return null;
        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getOwnerId(),
                task.getCreatedAt()
        );
    }

    public static Task toDomain(TaskEntity entity) {
        if (entity == null) return null;
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                TaskStatus.valueOf(entity.getStatus()),
                TaskPriority.valueOf(entity.getPriority()),
                entity.getOwnerId(),
                entity.getCreatedAt()
        );
    }
}
