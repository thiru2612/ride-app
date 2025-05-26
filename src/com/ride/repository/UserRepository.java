package com.ride.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ride.db.DatabaseConnection;

public class UserRepository {	
	public boolean createUser(String username,String password,String contact,int age) {
		try (Connection conn=DatabaseConnection.getConnection();
				PreparedStatement stmt=conn.prepareStatement("Insert into users (username, password, contact, age, role_id) values(?,?,?,?,?)")){
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.setString(3, contact);
				stmt.setInt(4, age);
				stmt.setInt(5, 2);
				
				return stmt.executeUpdate()>0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}