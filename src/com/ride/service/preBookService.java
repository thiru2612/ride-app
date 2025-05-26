package com.ride.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;
import com.ride.main.Main;

public class preBookService {
	public boolean viewPreBook(User u) {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement stmt=conn.prepareStatement("select r.date from user_ride ur left join rides r on "
					+ "ur.ride_id =r.id where ur.user_id=? and r.status='scheduled'");
			
			stmt.setInt(1, u.getId());
			ResultSet rs= stmt.executeQuery();
			if (!rs.next()) {  
				System.out.println("No PreBookig yet");
				return false;
			} else {
			    do {
			    	System.out.println("You have booked ride on " + rs.getString("date"));  
			    } while (rs.next());
			    return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in preBook service");
			return false;
		}
	}
	public void preBookRide(User u){
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter date and time for pre-booking (YYYY-MM-DD HH:MM:SS):");
		String inputTime = sc.nextLine();
		Timestamp preBookTime = Timestamp.valueOf(inputTime);

		Main m=new Main();
		m.bookRide(u, preBookTime);
	}
	public void cancelRide(User u) {
		Scanner sc = new Scanner(System.in);
		boolean pre= viewPreBook(u);
		if(pre) {
			System.out.println("which ride do you need to cancel?\nEnter the Timing:");
			String inputTime = sc.nextLine();
			Timestamp preBookTime = Timestamp.valueOf(inputTime);
			try (Connection conn=DatabaseConnection.getConnection()){
				PreparedStatement st1=conn.prepareStatement("select ur.ride_id from user_ride ur left join rides r "
						+ "on ur.ride_id=r.id where ur.user_id =? and r.date=?");
				st1.setInt(1, u.getId());
				st1.setTimestamp(2, preBookTime);
				
				ResultSet rs1=st1.executeQuery();
				int ride_id=-1;
				if(rs1.next()) {
					ride_id=rs1.getInt("ride_id");
				}
				if (ride_id != -1) {
				    PreparedStatement st2 = conn.prepareStatement("UPDATE rides SET status = 'Cancelled' WHERE id = ?");
				    st2.setInt(1, ride_id);
				    st2.executeUpdate();
	
				    PreparedStatement st3 = conn.prepareStatement("UPDATE transactions SET final_fair = final_fair * 0.2 WHERE ride_id = ?");
				    st3.setInt(1, ride_id);
				    st3.executeUpdate();
				    System.out.println("Cancelled pre booking on "+inputTime+" and 80 % refunded\n");
				} else {
				    System.out.println("Error: No ride found for cancellation.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
