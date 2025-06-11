package com.rideapp.api.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ride_db5";
    private static final String USER = "root";
    private static final String PASSWORD = "8274";
    public static Connection getConnection() {
        try {
            // ✅ Explicitly load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");  
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}