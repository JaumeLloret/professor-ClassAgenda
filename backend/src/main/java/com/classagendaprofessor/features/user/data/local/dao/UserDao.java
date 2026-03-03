package com.classagendaprofessor.features.user.data.local.dao;

import com.classagendaprofessor.features.user.data.local.entity.UserEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDao {
    private final Connection connection;
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public UserEntity insert(UserEntity userEntity) {
        String query = "INSERT INTO users (name, email, created_at) VALUES (?, ?, ?)";

        try (PreparedStatement createUserStmt = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            createUserStmt.setString(1, userEntity.getName());
            createUserStmt.setString(2, userEntity.getEmail());
            createUserStmt.setObject(3, userEntity.getCreatedAt());

            int affectedRows = createUserStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar el usuario");
            }

            try (ResultSet generatedKeys = createUserStmt.getGeneratedKeys()) {
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
        String query = "UPDATE USERS SET name = ?, email = ? WHERE id = ?";

        try (PreparedStatement updateUserStmt = connection.prepareStatement(query)) {

            updateUserStmt.setString(1, entityToUpdate.getName());
            updateUserStmt.setString(2, entityToUpdate.getEmail());
            updateUserStmt.setLong(3, entityToUpdate.getId());

            int affectedRows = updateUserStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo actualizar el usuario, el ID no existe.");
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al actualizar usuario", exception);
        }
    }

    public Optional<UserEntity> findById(Long idToSearch) {
        String query = "SELECT id, name, email, created_at FROM USERS WHERE id = ?";

        try (PreparedStatement readByIdUserStmt = connection.prepareStatement(query)) {

            readByIdUserStmt.setLong(1, idToSearch);

            try (ResultSet resultSet = readByIdUserStmt.executeQuery()) {
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

        try (PreparedStatement readByEmailUserStmt = connection.prepareStatement(query)) {

            readByEmailUserStmt.setString(1, email);

            try (ResultSet resultSet = readByEmailUserStmt.executeQuery()) {
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
        String query = "SELECT id, name, email, created_at FROM USERS";
        List<UserEntity> usersList = new ArrayList<>();

        try (PreparedStatement readAllUserStmt = connection.prepareStatement(query);
             ResultSet resultSet = readAllUserStmt.executeQuery()) {

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
        String query = "DELETE FROM USERS WHERE id = ?";

        try (PreparedStatement deleteUserStmt = connection.prepareStatement(query)) {

            deleteUserStmt.setLong(1, idToDelete);
            deleteUserStmt.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Error en BD al borrar usuario por ID", exception);
        }
    }
}
