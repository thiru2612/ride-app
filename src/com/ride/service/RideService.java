package com.ride.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS ride_count, MAX(date) AS Last_booked_date FROM rides WHERE user_id = ?")) {

	        stmt.setInt(1, user.getId());
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int rideCount = rs.getInt("ride_count");

	            if (rideCount == 0) {
	                newUserDiscount = 0.25;  // 🚀 25% Discount for new users
	            } else {
	                LocalDate LastBookedDate = rs.getTimestamp("Last_booked_date").toLocalDateTime().toLocalDate();
	                long daysSinceFirstRide = ChronoUnit.DAYS.between(LastBookedDate, LocalDate.now());

	                if (daysSinceFirstRide <= 1) {
	                    frequencyDiscount = 0.15;  // Daily User → 15%
	                } else if (daysSinceFirstRide <= 7) {
	                    frequencyDiscount = 0.10;  // Weekly User → 10%
	                } else if (daysSinceFirstRide <= 30) {
	                    frequencyDiscount = 0.05;  // Monthly User → 5%
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ageDiscount+ frequencyDiscount+newUserDiscount;
	}

	
	public String bookRide(String mode,String location,Double distance,Double price,User u) {
		double dis=calculateDiscount(u);
		LocalTime now = LocalTime.now();
        if ((now.isAfter(LocalTime.of(8, 30)) && now.isBefore(LocalTime.of(10, 0))) ||
            (now.isAfter(LocalTime.of(18, 30)) && now.isBefore(LocalTime.of(19, 0)))) {
            price *= 1.5;
        }
		boolean r=rr.addRide(mode, location, distance,distance*price*dis, u);
		if(r) {
			return new String("Mode: "+mode+" Location: "+location
					+" Distance: "+distance+" price: "+distance*price*dis+" Discount :"+dis);
		}
		else {
			return null;
		}
		
	}
//	
	public List<String> viewRide(User u){
		return rr.viewRides(u);
	}
}
