package com.ride.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;
import com.ride.repository.RideRepository;

public class RideService {

	static RideRepository rr=new RideRepository();
	public List<String> viewRide(){
		return rr.viewRides();
	}
	
	public double calculateDiscount(User user) {
	    double ageDiscount = 0;
	    double frequencyDiscount = 0;
	    double newUserDiscount = 0;

	    // Step 1: Apply Age-Based Discount
	    if (user.getAge() > 60) {
	        ageDiscount = 0.35;  // 35% Discount
	    } else if (user.getAge() >= 40 && user.getAge() <= 60) {
	        ageDiscount = 0.25;  // 25% Discount
	    }

	    // Step 2: Check Booking Frequency & New User Status
	    if (user.getUsage().equals("New")||user.getUsage().equals("unknown")) {
	    	newUserDiscount = 0.25;  // 25% Discount for new users
	    } 
	    else if (user.getUsage().equals("Daily")) {
	        frequencyDiscount = 0.15;  // Daily User → 15%
	    }
	    else if (user.getUsage().equals("Weekly")) {
	    	frequencyDiscount = 0.10;  // Weekly User → 10%
	    } 
	    else if (user.getUsage().equals("Monthly")) {    
	    	frequencyDiscount = 0.05;  // Monthly User → 5%
	    }

	    return ageDiscount+ frequencyDiscount+newUserDiscount;
	}

	
	public reviewService bookRide(String mode,int plocation,int dlocation,Double price,User u,Timestamp d) {
		
		try(Connection conn=DatabaseConnection.getConnection()) {
			PreparedStatement stmt=conn.prepareStatement("select distance from travel_routes where pickup_loc=? and destination_loc=?");
			stmt.setInt(1, plocation);
			stmt.setInt(2, dlocation);
			ResultSet rs=stmt.executeQuery();
			double distance;
			if (rs.next()) {  // ✅ Moves cursor to first row before accessing data
			    distance = rs.getDouble("distance");
			} else {
			    System.out.println("Error: No travel route found for given locations.");
			    return null;
			}

			double dis=calculateDiscount(u);
			LocalTime now = LocalTime.now();
	        if ((now.isAfter(LocalTime.of(8, 30)) && now.isBefore(LocalTime.of(10, 0))) ||
	            (now.isAfter(LocalTime.of(18, 30)) && now.isBefore(LocalTime.of(19, 0)))) {
	            price *= 1.5;
	        }
	        if(d!=null)
	        	price+=50;
			reviewService r=rr.addRide(mode, plocation,dlocation, distance,distance*price,(distance*price)-(distance*price*dis), u,d,dis);
			if(r!=null) {
				System.out.println("Booking successful\nGenerating invoice...");
				System.out.println("Mode: "+mode+" Pickup Location: "+ShowRide.findLoc(plocation)+" Destination:"+ShowRide.findLoc(dlocation)
						+" Distance: "+distance+" price: "+distance*price+" Final-fair(Discounted) :"+(double)((distance*price)-(distance*price*dis)));
				return r;
			}
			else {
				return r;
			}			
		} catch (SQLException e) {
			System.out.println("error in the booking of ride");
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> viewRide(User u){
		return rr.viewRides(u);
	}
}
