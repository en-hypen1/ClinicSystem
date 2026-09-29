package com.clinic.dao;

import com.clinic.database.DatabaseConnection;
import com.clinic.model.StaffTableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // =====================================================
    // INSERT STAFF
    // =====================================================

    public void insertStaff(
            Connection connection,
            long userId,
            String firstName,
            String lastName,
            String phone,
            String specialisation,
            String licenseNumber
    ) throws SQLException {

        String sql = """
                INSERT INTO staff
                    (
                        user_id,
                        first_name,
                        last_name,
                        phone,
                        specialisation,
                        license_number
                    )
                VALUES
                    (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, phone);

            if (specialisation == null
                    || specialisation.isBlank()) {

                statement.setNull(
                        5,
                        java.sql.Types.VARCHAR
                );

            } else {

                statement.setString(
                        5,
                        specialisation
                );
            }

            if (licenseNumber == null
                    || licenseNumber.isBlank()) {

                statement.setNull(
                        6,
                        java.sql.Types.VARCHAR
                );

            } else {

                statement.setString(
                        6,
                        licenseNumber
                );
            }

            statement.executeUpdate();
        }
    }
    public List<StaffTableModel> getAllStaff() throws SQLException {

        List<StaffTableModel> staffList = new ArrayList<>();

        String sql = """
            SELECT
                u.id AS user_id,
                s.first_name,
                s.last_name,
                u.email,
                s.phone,
                r.name AS role_name,
                u.is_active,
                u.profile_photo
            FROM staff s
            JOIN users u ON s.user_id = u.id
            JOIN roles r ON u.role_id = r.id
            ORDER BY s.first_name, s.last_name
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                String firstName =
                        resultSet.getString("first_name");

                String lastName =
                        resultSet.getString("last_name");

                String fullName =
                        firstName + " " + lastName;

                String status =
                        resultSet.getBoolean("is_active")
                                ? "Active"
                                : "Inactive";

                staffList.add(
                        new StaffTableModel(
                                resultSet.getLong("user_id"),
                                fullName,
                                resultSet.getString("email"),
                                resultSet.getString("phone"),
                                resultSet.getString("role_name"),
                                status,
                                resultSet.getString("profile_photo")
                        )
                );
            }
        }

        return staffList;
    }
}