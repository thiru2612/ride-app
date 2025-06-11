package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rideapp.api.db.DatabaseConnection;

public class LoginUser {
    public int login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT id, username, role_id, age, us.uUsage FROM users u " +
                 "INNER JOIN user_status us ON u.id=us.user_id WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            int roleId=-1;
            int userId=-1;
            if (rs.next()) {
            	roleId= rs.getInt("role_id");
                String role = (roleId == 2) ? "user" : "admin";
                userId=rs.getInt("id");
            }
            return userId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}