package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rideapp.api.db.DatabaseConnection;

public class CreateUser {
    public int createUser(String uname, String pass, String contact, int age) {
        int userId = -1;

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ ERROR: Database connection failed!");
                return -1;
            }
            
            conn.setAutoCommit(false); // ✅ Disable auto-commit for controlled transaction
            
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, contact, age, role_id) VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, uname);
                stmt.setString(2, pass);
                stmt.setString(3, contact);
                stmt.setInt(4, age);
                stmt.setInt(5, 2);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    }
                    rs.close();
                }
            }

            // ✅ Log user ID retrieval
            System.out.println("User ID from DB: " + userId);

            // ✅ Insert user into `user_status` table
            if (userId > 0) {
                try (PreparedStatement st = conn.prepareStatement("INSERT INTO user_status (user_id) VALUES (?)")) {
                    st.setInt(1, userId);
                    st.executeUpdate();
                }
            }

            conn.commit(); // ✅ Ensure transaction is committed
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }
}
