package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashMap;

import com.rideapp.api.db.DatabaseConnection;

public class BookRideService {
	public double calculateDiscount(int userId,String usage,int age) {
	    double ageDiscount = 0;
	    double frequencyDiscount = 0;
	    double newUserDiscount = 0;
	    // Step 1: Apply Age-Based Discount
	    if (age > 60) {
	        ageDiscount = 0.35;  // 35% Discount
	    } else if (age >= 40 && age <= 60) {
	        ageDiscount = 0.25;  // 25% Discount
	    }

	    // Step 2: Check Booking Frequency & New User Status
	    if (usage.equals("New")||usage.equals("unknown")) {
	    	newUserDiscount = 0.25;  // 25% Discount for new users
	    } 
	    else if (usage.equals("Daily")) {
	        frequencyDiscount = 0.15;  // Daily User → 15%
	    }
	    else if (usage.equals("Weekly")) {
	    	frequencyDiscount = 0.10;  // Weekly User → 10%
	    } 
	    else if (usage.equals("Monthly")) {    
	    	frequencyDiscount = 0.05;  // Monthly User → 5%
	    }

	    return ageDiscount+ frequencyDiscount+newUserDiscount;
	}

	
	public HashMap<String, Double> bookRide(int user_id,String mode,int plocation,int dlocation,Timestamp d,
			String pmode,String review,int rating,String tStatus) {
		
		try(Connection conn=DatabaseConnection.getConnection()) {
			PreparedStatement stmt=conn.prepareStatement("select distance from travel_routes where (pickup_loc=? and destination_loc=?) or (pickup_loc=? and destination_loc=?)");
			stmt.setInt(1, plocation);
			stmt.setInt(2, dlocation);
			stmt.setInt(3, dlocation);
			stmt.setInt(4, plocation);
			ResultSet rs=stmt.executeQuery();
			double distance;
			if (rs.next()) {  // ✅ Moves cursor to first row before accessing data
			    distance = rs.getDouble("distance");
			} else {
			    System.out.println("Error: No travel route found for given locations.");
			    return null;
			}

			
			
			PreparedStatement st=conn.prepareStatement("select age,us.uUsage from users u inner join user_status us on us.user_id=u.id "
					+ "where u.id=? ;");
			st.setInt(1, user_id);
			ResultSet rs3=st.executeQuery();
			String usage="";
			int age=18;
			if(rs3.next()) {
				age=rs3.getInt("age");
				usage=rs3.getString("uUsage");
			}
			Double price=0.0;
			switch (mode) {
				case "Bike":
				case "bike":
					price=50.0;
					break;
				case "Car":
				case "car":
					price=100.0;
					break;
				case "Auto":
				case "auto":
					price=80.0;
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + mode);
			}
			
			double dis=calculateDiscount(user_id,usage,age);
			LocalTime now = LocalTime.now();
	        if ((now.isAfter(LocalTime.of(8, 30)) && now.isBefore(LocalTime.of(10, 0))) ||
	            (now.isAfter(LocalTime.of(18, 30)) && now.isBefore(LocalTime.of(19, 0)))) {
	            price *= 1.5;
	        }
	        if(d!=null)
	        	price+=50;
	        BookRide br=new BookRide();
	        return br.addRide(user_id,mode, plocation,dlocation, distance*price,(distance*price)-(distance*price*dis),d,dis,pmode,review,rating,tStatus);
					
		} catch (SQLException e) {
			System.out.println("error in the booking of ride");
			e.printStackTrace();
			return null;
		}
	}
}
