package com.clinic.dao;

import com.clinic.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public List<String> getStaffRoles() {

        List<String> roles = new ArrayList<>();

        String sql = """
                SELECT name
                FROM roles
                WHERE LOWER(name) IN ('doctor', 'receptionist')
                ORDER BY name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                roles.add(resultSet.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }
}