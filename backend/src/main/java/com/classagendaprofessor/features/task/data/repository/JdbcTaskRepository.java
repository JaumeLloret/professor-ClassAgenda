package com.classagendaprofessor.features.task.data.repository;

import com.classagendaprofessor.features.task.data.local.dao.TaskDao;
import com.classagendaprofessor.features.task.data.local.entity.TaskEntity;
import com.classagendaprofessor.features.task.data.mapper.TaskMapper;
import com.classagendaprofessor.features.task.domain.model.Task;
import com.classagendaprofessor.features.task.domain.model.TaskStatus;
import com.classagendaprofessor.features.task.domain.repository.TaskRepository;
import com.classagendaprofessor.features.user.data.local.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcTaskRepository implements TaskRepository {
    private final TaskDao taskDao;

    public JdbcTaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    @Override
    public Task save(Task task) {
        TaskEntity entityToSave = TaskMapper.toEntity(task);

        if (entityToSave.getId() == null) {
            TaskEntity insertedEntity = taskDao.insert(entityToSave);
            return TaskMapper.toDomain(insertedEntity);
        } else {
            taskDao.update(entityToSave);
            return task;
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskDao.findById(id).map(TaskMapper::toDomain);
    }

    @Override
    public List<Task> findByOwnerId(long ownerId) {
        List<TaskEntity> entityList = taskDao.findByOwnerId(ownerId);

        return entityList.stream()
                .map(TaskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByOwnerIdAndStatus(long ownerId, TaskStatus status) {
        List<TaskEntity> entityList = taskDao.findByOwnerIdAndStatus(ownerId, status.name());

        return entityList.stream()
                .map(TaskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        taskDao.deleteById(id);
    }

}
