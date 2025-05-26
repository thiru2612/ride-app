package com.ride.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;
import com.ride.repository.UserRepository;
public class UserService {

    private UserRepository urepo=new UserRepository();
	public User login(String username, String password) {
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT id,username, password, role, age FROM users WHERE username = ? AND password = ?")) {

	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new User(rs.getString("username"), rs.getString("role"), rs.getInt("age"),rs.getInt("id"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

    public boolean createUser(String username,String password,String contactNumber,int age){
        return urepo.createUser(username, password, contactNumber,age);
    }
}
