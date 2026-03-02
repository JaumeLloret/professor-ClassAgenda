package com.classagendaprofessor.features.task.data.local.dao;

import com.classagendaprofessor.features.task.data.local.connection.DbConnectionFactory;
import com.classagendaprofessor.features.task.data.local.entity.TaskEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDao {
    private final DbConnectionFactory connectionFactory;

    public TaskDao(DbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public TaskEntity insert(TaskEntity entity) {
        String query = "INSERT INTO TASKS (title, description, status, priority, owner_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getDescription());
            pstmt.setString(3, entity.getStatus());
            pstmt.setString(4, entity.getPriority());
            pstmt.setLong(5, entity.getOwnerId());
            pstmt.setObject(6, entity.getCreatedAt());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
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

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, entity.getTitle());
            pstmt.setString(2, entity.getDescription());
            pstmt.setString(3, entity.getStatus());
            pstmt.setString(4, entity.getPriority());
            pstmt.setLong(5, entity.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update fallido: La tarea con ese ID no existe.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al actualizar la tarea", e);
        }
    }

    public Optional<TaskEntity> findById(Long id) {
        String query = "SELECT id, title, description, status, priority, owner_id, created_at FROM TASKS WHERE id = ?";

        try (
            Connection connection = connectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)
        ) {

            pstmt.setLong(1, id);

            try (ResultSet resultSet = pstmt.executeQuery()) {
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

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, ownerId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
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

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, ownerId);
            pstmt.setString(2, status);

            try (ResultSet resultSet = pstmt.executeQuery()) {
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

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al borrar la tarea", e);
        }
    }
}
