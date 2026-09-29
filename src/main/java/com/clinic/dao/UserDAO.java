package com.clinic.dao;

import com.clinic.database.DatabaseConnection;
import com.clinic.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findByEmail(String email) {

        String sql = """
                SELECT u.id,
                       u.email,
                       u.password_hash,
                       r.name AS role,
                       u.profile_photo,
                       u.is_active,
                       u.created_at
                FROM users u
                JOIN roles r ON u.role_id = r.id
                WHERE u.email = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(
                            resultSet.getString("password_hash")
                    );
                    user.setRole(resultSet.getString("role"));
                    user.setProfilePhoto(
                            resultSet.getString("profile_photo")
                    );
                    user.setActive(
                            resultSet.getBoolean("is_active")
                    );

                    if (resultSet.getTimestamp("created_at") != null) {
                        user.setCreatedAt(
                                resultSet.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );
                    }

                    return user;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // =====================================================
    // CHECK IF EMAIL ALREADY EXISTS
    // =====================================================

    public boolean emailExists(
            Connection connection,
            String email
    ) throws SQLException {

        String sql = """
                SELECT 1
                FROM users
                WHERE email = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    // =====================================================
    // INSERT USER
    // =====================================================

    public long insertUser(
            Connection connection,
            String email,
            String passwordHash,
            int roleId,
            String profilePhoto
    ) throws SQLException {

        String sql = """
                INSERT INTO users
                    (email, password_hash, profile_photo, is_active, role_id)
                VALUES
                    (?, ?, ?, true, ?)
                RETURNING id
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, passwordHash);
            statement.setString(3, profilePhoto);
            statement.setInt(4, roleId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
        }

        throw new SQLException(
                "User could not be created."
        );
    }


    // =====================================================
    // GET ROLE ID
    // =====================================================

    public int getRoleId(
            Connection connection,
            String roleName
    ) throws SQLException {

        String sql = """
                SELECT id
                FROM roles
                WHERE LOWER(name) = LOWER(?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, roleName);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }

        throw new SQLException(
                "Role not found: " + roleName
        );
    }
}