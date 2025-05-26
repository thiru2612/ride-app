package com.ride.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;
import com.ride.service.reviewService;
public class RideRepository {
	
//	public reviewService addRide(String mode, int plocation, int dlocation, double distance, double actual, double fin, User u, Timestamp t, double dis) {
//        Scanner sc = new Scanner(System.in);
//
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            // Ensure timestamp is set
//            if (t == null) t = new Timestamp(System.currentTimeMillis());
//
//            // Insert ride into `rides` table
//            PreparedStatement rideStmt = conn.prepareStatement(
//                "INSERT INTO rides (mode, distance, date) VALUES (?, ?, ?);",
//                PreparedStatement.RETURN_GENERATED_KEYS
//            );
//            rideStmt.setString(1, mode);
//            rideStmt.setDouble(2, distance);
//            rideStmt.setTimestamp(3, t);
//            rideStmt.executeUpdate();
//            
//            // Retrieve generated ride ID
//            ResultSet rs = rideStmt.getGeneratedKeys();
//            int rideId = -1;
//            if (rs.next()) {
//                rideId = rs.getInt(1);
//            } else {
//                System.out.println("Error: Ride ID retrieval failed.");
//                return null;
//            }
//
//            // Insert into `travel_routes` table
//            PreparedStatement routeStmt = conn.prepareStatement(
//                "INSERT INTO travel_routes (ride_id, pickup_loc, des_loc) VALUES (?, ?, ?);"
//            );
//            routeStmt.setInt(1, rideId);
//            routeStmt.setInt(2, plocation);
//            routeStmt.setInt(3, dlocation);
//            routeStmt.executeUpdate();
//
//            // Prompt user for payment mode
//            System.out.println("Enter mode of payment:\n1.UPI\n2.Net banking\n3.Cash");
//            int pmode = sc.nextInt();
//            String paymentMode;
//            switch (pmode) {
//                case 1: paymentMode = "UPI"; break;
//                case 2: paymentMode = "Net Banking"; break;
//                case 3: paymentMode = "Cash"; break;
//                default: throw new IllegalArgumentException("Invalid payment mode: " + pmode);
//            }
//
//            // Insert into `transactions` table
//            PreparedStatement tranStmt = conn.prepareStatement(
//                "INSERT INTO transactions (actual_fair, final_fair, discount, mode) VALUES (?, ?, ?, ?);",
//                PreparedStatement.RETURN_GENERATED_KEYS
//            );
//            tranStmt.setDouble(1, actual);
//            tranStmt.setDouble(2, fin);
//            tranStmt.setDouble(3, dis);
//            tranStmt.setString(4, paymentMode);
//            tranStmt.executeUpdate();
//
//            // Retrieve generated transaction ID
//            ResultSet tranRs = tranStmt.getGeneratedKeys();
//            int tranId = -1;
//            if (tranRs.next()) {
//                tranId = tranRs.getInt(1);
//            } else {
//                System.out.println("Error: Transaction ID retrieval failed.");
//                return null;
//            }
//
//            // Insert into `user_ride` table (Triggers `uUsage` update)
//            PreparedStatement userRideStmt = conn.prepareStatement(
//                "INSERT INTO user_ride (user_id, ride_id, date) VALUES (?, ?, ?);"
//            );
//            userRideStmt.setInt(1, u.getId());
//            userRideStmt.setInt(2, rideId);
//            userRideStmt.setTimestamp(3, t);
//            userRideStmt.executeUpdate();
//
//            // Insert into `ride_pay` table
//            PreparedStatement ridePayStmt = conn.prepareStatement(
//                "INSERT INTO ride_pay (ride_id, transfer_id, transfer_date) VALUES (?, ?, ?);"
//            );
//            ridePayStmt.setInt(1, rideId);
//            ridePayStmt.setInt(2, tranId);
//            ridePayStmt.setTimestamp(3, t);
//            ridePayStmt.executeUpdate();
//            System.out.println("Ride added successfully!");
//            return new reviewService(rideId,u.getId());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

	public reviewService addRide(String mode, int plocation, int dlocation, double distance, double actual, double fin, User u, Timestamp t, double dis) {
	    Scanner sc = new Scanner(System.in);

	    try (Connection conn = DatabaseConnection.getConnection()) {
	        // Ensure timestamp is set\
	    	String status="scheduled"; 
	        if (t == null) {
	        	t = new Timestamp(System.currentTimeMillis());
	        	status=new String("Booked");
	        }

	        // Insert ride into `rides` table
	        PreparedStatement rideStmt = conn.prepareStatement(
	            "INSERT INTO rides (rMode, date, status) VALUES (?, ?, ?);",  // ✅ Updated column names
	            PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        rideStmt.setString(1, mode);
	        rideStmt.setTimestamp(2, t);
	        rideStmt.setString(3, status);
	        rideStmt.executeUpdate();

	        // Retrieve generated ride ID
	        ResultSet rs = rideStmt.getGeneratedKeys();
	        int rideId = -1;
	        if (rs.next()) {
	            rideId = rs.getInt(1);
	        } else {
	            System.out.println("Error: Ride ID retrieval failed.");
	            return null;
	        }

	        // Insert into `travel_routes` table
	        PreparedStatement routeStmt = conn.prepareStatement(
	            "INSERT INTO travel_routes (pickup_loc, destination_loc, distance) VALUES (?, ?, ?);"  // ✅ Corrected column names
	        );
	        routeStmt.setInt(1, plocation);
	        routeStmt.setInt(2, dlocation);
	        routeStmt.setDouble(3, distance);  // ✅ Ensured `distance` is included
	        routeStmt.executeUpdate();

	        // Prompt user for payment mode
	        System.out.println("Enter mode of payment:\n1.UPI\n2.Net Banking\n3.Cash");
	        int pmode = sc.nextInt();
	        String paymentMode;
	        switch (pmode) {
	            case 1: paymentMode = "UPI"; break;
	            case 2: paymentMode = "Net Banking"; break;
	            case 3: paymentMode = "Cash"; break;
	            default: throw new IllegalArgumentException("Invalid payment mode: " + pmode);
	        }

	        // Insert into `transactions` table
	        PreparedStatement tranStmt = conn.prepareStatement(
	            "INSERT INTO transactions (actual_fair, final_fair, discount, tmode, ride_id) VALUES (?, ?, ?, ?, ?);",  // ✅ Updated column names
	            PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        tranStmt.setDouble(1, actual);
	        tranStmt.setDouble(2, fin);
	        tranStmt.setDouble(3, dis);
	        tranStmt.setString(4, paymentMode);
	        tranStmt.setInt(5, rideId);
	        tranStmt.executeUpdate();

	        // Retrieve generated transaction ID
	        ResultSet tranRs = tranStmt.getGeneratedKeys();
	        int tranId = -1;
	        if (tranRs.next()) {
	            tranId = tranRs.getInt(1);
	        } else {
	            System.out.println("Error: Transaction ID retrieval failed.");
	            return null;
	        }

	        // Insert into `user_ride` table (Triggers `uUsage` update)
	        PreparedStatement userRideStmt = conn.prepareStatement(
	            "INSERT INTO user_ride (user_id, ride_id,rType) VALUES (?, ?,?);"  // ✅ Removed `date` column (handled in default timestamp)
	        );
	        userRideStmt.setInt(1, u.getId());
	        userRideStmt.setInt(2, rideId);
	        if(status.equals("scheduled")) {
	        	userRideStmt.setString(3, status);
	        }
	        else {
	        	userRideStmt.setString(3, "normal");
			}
	        userRideStmt.executeUpdate();
	        
	        System.out.println("Ride added successfully!");
	        return new reviewService(rideId, u.getId(),tranId);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
    // Method to view rides without user details
    public List<String> viewRides() {
        List<String> rideDetails = new ArrayList<>();
        
        try(Connection conn=DatabaseConnection.getConnection()){
        	PreparedStatement p = conn.prepareStatement(
        		    "SELECT r.rMode, r.date, r.status, " +
        		    "COALESCE(t.actual_fair, 0) AS act, " +
        		    "COALESCE(t.final_fair, 0) AS final, " +
        		    "COALESCE(t.discount, 0) AS discount, " +
        		    "tr.pickup_loc AS pickup, " +
        		    "tr.destination_loc AS destination, " +
        		    "tr.distance ,"+
        		    "COALESCE(pr.rating, 'No Rating') AS review, " +
        		    "COALESCE(u.uUsage, 'Unknown') AS `usage` " +  // ✅ Now correctly fetching from users table
        		    "FROM rides r " +
        		    "LEFT JOIN travel_routes tr ON r.id = tr.id " +
        		    "LEFT JOIN transactions t ON r.id = t.ride_id " +
        		    "LEFT JOIN reviews pr ON r.id = pr.ride_id " +
        		    "LEFT JOIN user_ride ur ON r.id = ur.ride_id " +  // ✅ Keeping user_ride for reference
        		    "LEFT JOIN users u ON ur.user_id = u.id " +  // ✅ Correctly joining users
        		    "ORDER BY r.date DESC;"
        		);



        	

        	ResultSet rs=p.executeQuery();
        	
        	while (rs.next()) {
        	    rideDetails.add("Mode: " + rs.getString("rMode") + 
        	                    " Distance: " + rs.getString("distance") + 
        	                    " Final Price: " + rs.getString("final") + 
        	                    " Pickup: " + rs.getString("pickup") + 
        	                    " Destination: " + rs.getString("destination") + 
        	                    " Date: " + rs.getString("date") + 
        	                    " Ride Status: " + rs.getString("status") + 
        	                    " Review: " + rs.getString("review") + 
        	                    " User Type: " + rs.getString("usage"));
        	}
        }
        catch (SQLException e) {
        	e.printStackTrace();
		}
        return rideDetails;
    }
    
    
    public List<String> viewRides(User u) {
    	
    	List<String> rideDetails = new ArrayList<>();
    	try (Connection conn=DatabaseConnection.getConnection()){
    		
    		PreparedStatement stmt = conn.prepareStatement(
    			    "SELECT " +
    			    "    r.rMode, r.date, r.status, tr.distance, " +
    			    "    COALESCE(t.actual_fair, 0) AS act, " +
    			    "    COALESCE(t.final_fair, 0) AS final, " +
    			    "    COALESCE(t.discount, 0) AS discount, " +
    			    "    tr.pickup_loc AS pickup, " +
    			    "    tr.destination_loc AS destination, " +
    			    "    COALESCE(pr.rating, 'No Rating') AS review, " +
    			    "    COALESCE(u.uUsage, 'Unknown') AS `usage` " +
    			    "FROM rides r " +
    			    "LEFT JOIN travel_routes tr ON r.id = tr.id " +
    			    "LEFT JOIN transactions t ON r.id = t.ride_id " +
    			    "LEFT JOIN reviews pr ON r.id = pr.ride_id " +
    			    "LEFT JOIN user_ride ur ON r.id = ur.ride_id " +
    			    "LEFT JOIN users u ON ur.user_id = u.id " +
    			    "WHERE u.id = ? " +  // ✅ Corrected user_id reference in the users table
    			    "ORDER BY r.date DESC;"
    			);


        	
    		stmt.setInt(1, u.getId());
    		
    		ResultSet rs=stmt.executeQuery();
    		
    		while(rs.next()) {
    		    rideDetails.add("Mode: " + rs.getString("rMode") + 
    		                    " Distance: " + rs.getString("distance") + 
    		                    " Actual Price: " + rs.getString("act") + 
    		                    " Final Price: " + rs.getString("final") + 
    		                    " Discount: " + rs.getDouble("discount") + 
    		                    " Pickup: " + rs.getString("pickup") + 
    		                    " Destination: " + rs.getString("destination") +  // ✅ Corrected "des" to "destination"
    		                    " Date: " + rs.getString("date") + 
    		                    " Ride Status: " + rs.getString("status") + 
    		                    " Review: " + rs.getString("review") + 
    		                    " User Type: " + rs.getString("usage"));
    		}

    		return rideDetails;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
}