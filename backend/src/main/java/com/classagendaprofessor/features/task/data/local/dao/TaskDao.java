package com.classagendaprofessor.features.task.data.local.dao;

import com.classagendaprofessor.features.task.data.local.entity.TaskEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDao {
    private final Connection connection;

    public TaskDao(Connection connection) {
        this.connection = connection;
    }

    public TaskEntity insert(TaskEntity entity) {
        String query = "INSERT INTO TASKS (title, description, status, priority, owner_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement createTaskStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            createTaskStmt.setString(1, entity.getTitle());
            createTaskStmt.setString(2, entity.getDescription());
            createTaskStmt.setString(3, entity.getStatus());
            createTaskStmt.setString(4, entity.getPriority());
            createTaskStmt.setLong(5, entity.getOwnerId());
            createTaskStmt.setObject(6, entity.getCreatedAt());

            createTaskStmt.executeUpdate();

            try (ResultSet generatedKeys = createTaskStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    return entity;
                } else {
                    throw new SQLException("No se pudo obtener el ID autogenerado para Task.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando la tarea en BD", e);
        }
    }

    public void update(TaskEntity entity) {
        String query = "UPDATE TASKS SET title = ?, description = ?, status = ?, priority = ? WHERE id = ?";

        try (PreparedStatement updateTaskStmt = connection.prepareStatement(query)) {

            updateTaskStmt.setString(1, entity.getTitle());
            updateTaskStmt.setString(2, entity.getDescription());
            updateTaskStmt.setString(3, entity.getStatus());
            updateTaskStmt.setString(4, entity.getPriority());
            updateTaskStmt.setLong(5, entity.getId());

            int affectedRows = updateTaskStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update fallido: La tarea con ese ID no existe.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al actualizar la tarea", e);
        }
    }

    public Optional<TaskEntity> findById(Long id) {
        String query = "SELECT id, title, description, status, priority, owner_id, created_at FROM TASKS WHERE id = ?";

        try (PreparedStatement findTaskByIdStmt = connection.prepareStatement(query)) {

            findTaskByIdStmt.setLong(1, id);

            try (ResultSet resultSet = findTaskByIdStmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al buscar tarea por ID", e);
        }
    }

    public List<TaskEntity> findByOwnerId(Long ownerId) {
        String query = "SELECT id, title, description, status, priority, owner_id, created_at FROM TASKS WHERE owner_id = ?";
        List<TaskEntity> list = new ArrayList<>();

        try (PreparedStatement findTaskByOwnerStmt = connection.prepareStatement(query)) {

            findTaskByOwnerStmt.setLong(1, ownerId);

            try (ResultSet resultSet = findTaskByOwnerStmt.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToEntity(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando tareas del usuario", e);
        }
    }

    public List<TaskEntity> findByOwnerIdAndStatus(Long ownerId, String status) {
        String query = "SELECT id, title, description, status, priority, owner_id, created_at " +
                "FROM TASKS WHERE owner_id = ? AND status = ?";
        List<TaskEntity> list = new ArrayList<>();

        try (PreparedStatement findTaskByOwnerAndStatusStmt = connection.prepareStatement(query)) {

            findTaskByOwnerAndStatusStmt.setLong(1, ownerId);
            findTaskByOwnerAndStatusStmt.setString(2, status);

            try (ResultSet resultSet = findTaskByOwnerAndStatusStmt.executeQuery()) {
                while (resultSet.next()) {
                    list.add(mapResultSetToEntity(resultSet));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando tareas por dueño y estado", e);
        }
    }

    private TaskEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new TaskEntity(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getString("priority"),
                rs.getLong("owner_id"),
                rs.getObject("created_at", LocalDateTime.class)
        );
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM TASKS WHERE id = ?";

        try (PreparedStatement deleteTaskStmt = connection.prepareStatement(query)) {

            deleteTaskStmt.setLong(1, id);
            deleteTaskStmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al borrar la tarea", e);
        }
    }
}
