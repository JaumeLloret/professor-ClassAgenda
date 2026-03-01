package com.classagendaprofessor.features.user.data.local.dao;

import com.classagendaprofessor.features.user.data.local.connection.DbConnectionFactory;
import com.classagendaprofessor.features.user.data.local.entity.UserEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDao {
    private final DbConnectionFactory connectionFactory;
    public UserDao(DbConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public UserEntity insert(UserEntity userEntity) {
        String query = "INSERT INTO users (name, email, created_at) VALUES (?, ?, ?)";

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, userEntity.getName());
            preparedStatement.setString(2, userEntity.getEmail());
            preparedStatement.setObject(3, userEntity.getCreatedAt());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar el usuario");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userEntity.setId(generatedKeys.getLong(1));
                    return userEntity;
                } else {
                    throw new SQLException("No se pudo obtener el ID autogenerado.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al insertar usuario", e);
        }
    }

    public void update(UserEntity entityToUpdate) {
        String updateQuery = "UPDATE USERS SET name = ?, email = ? WHERE id = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, entityToUpdate.getName());
            preparedStatement.setString(2, entityToUpdate.getEmail());
            preparedStatement.setLong(3, entityToUpdate.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo actualizar el usuario, el ID no existe.");
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al actualizar usuario", exception);
        }
    }

    public Optional<UserEntity> findById(Long idToSearch) {
        String selectQuery = "SELECT id, name, email, created_at FROM USERS WHERE id = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setLong(1, idToSearch);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al buscar usuario por ID", exception);
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        String query = "SELECT id, name, email, created_at FROM USERS WHERE email = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en BD al buscar usuario por email", e);
        }
    }

    public List<UserEntity> findAll() {
        String selectAllQuery = "SELECT id, name, email, created_at FROM USERS";
        List<UserEntity> usersList = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                usersList.add(mapResultSetToEntity(resultSet));
            }
            return usersList;

        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al recuperar todos los usuarios", exception);
        }
    }

    private UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getObject("created_at", LocalDateTime.class)
        );
    }

    public void deleteById(Long idToDelete) {
        String deleteQuery = "DELETE FROM USERS WHERE id = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setLong(1, idToDelete);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al borrar usuario por ID", exception);
        }
    }
}
