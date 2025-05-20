package com.ride.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ride.db.DatabaseConnection;

public class UserRepository {
//	public User login(String username,String password) {
//		List<User> userList=users.stream()
//                .filter(user->user.getUsername().equals(username)&&user.getPassword().equals(password))
//                .collect(Collectors.toList());
//		if(userList.isEmpty()) {
//			return null;
//		}
//		return userList.get(0);
//	}
//	
	public boolean createUser(String username,String password,String contact,int age) {
		try (Connection conn=DatabaseConnection.getConnection();
				PreparedStatement stmt=conn.prepareStatement("Insert into users (username, password, contact, age, role) values(?,?,?,?,?)")){
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.setString(3, contact);
				stmt.setInt(4, age);
				stmt.setString(5, "user");
				
				return stmt.executeUpdate()>0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	public User login(String username,String  password) {
//		try (Connection conn=DatabaseConnection.getConnection()){
//			Statement stmt=conn.createStatement();
//			
//			ResultSet rs=stmt.executeQuery("Select username,password from user where username=${username");
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
