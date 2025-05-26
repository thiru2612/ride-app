package com.ride.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ride.db.DatabaseConnection;
public class ShowRide {

	public static void showServiceArea() {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement p=conn.prepareStatement("select * from locations order by 1");
			ResultSet rs=p.executeQuery();
			int i=0;
			while(rs.next()) {
				i++;
				System.out.print(" "+rs.getInt("id")+"."+rs.getString("location")+"\t");
				if(i%5==0)
					System.out.println();
			}
			System.out.println();						
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static String findLoc(int x) {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement p=conn.prepareStatement("select location from locations where id=?");
			p.setInt(1, x);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				return rs.getString("location");
			}
			
			return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
