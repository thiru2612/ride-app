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
	         PreparedStatement stmt = conn.prepareStatement("SELECT id,username,role_id, age,uUsage FROM users WHERE username = ? AND password = ?")) {

	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	if(rs.getInt("role_id")==2)
	            return new User(rs.getString("username"), "user", rs.getInt("age"),rs.getInt("id"),rs.getString("uUsage"));
	        	return new User(rs.getString("username"), "admin", rs.getInt("age"),rs.getInt("id"),rs.getString("uUsage"));
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
