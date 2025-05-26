package com.ride.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;

public class transactionService {
	public static void viewT(User u) {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement st1=conn.prepareStatement("SELECT t.final_fair, t.tmode, t.tStatus, t.date\r\n"
					+ "FROM user_ride ur\r\n"
					+ "LEFT JOIN rides r ON ur.ride_id = r.id\r\n"
					+ "LEFT JOIN transactions t ON r.id = t.ride_id "
					+ "where ur.user_id=?;\r\n");
			
			
			st1.setInt(1, u.getId());
			
			ResultSet rs=st1.executeQuery();
			
			while(rs.next()) {
				System.out.println("Mode :"+rs.getString("tmode")+
						" Amount:"+rs.getDouble("final_fair")+
						" Date:"+rs.getTimestamp("date")+""
						+ " Status:"+rs.getString("tStatus"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
