package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Scanner;

import com.rideapp.api.db.DatabaseConnection;


public class BookRide {
	public HashMap<String, Double> addRide(int userId,String mode, int plocation, int dlocation, double actual,
			double fin, Timestamp t, double dis,String payMode,String review,int rating,
			String tStatus) {
	    Scanner sc = new Scanner(System.in);

	    try (Connection conn = DatabaseConnection.getConnection()) {
	    	String status="scheduled"; 
	        if (t == null) {
	        	t = new Timestamp(System.currentTimeMillis());
	        	status=new String("Booked");
	        }

	       
	        // Insert ride into `rides` table
	        PreparedStatement rideStmt = conn.prepareStatement(
	            "INSERT INTO rides (rMode, date) VALUES (?, ?);",
	            PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        rideStmt.setString(1, mode);
	        rideStmt.setTimestamp(2, t);
	        
	        
//	        rideStmt.setString(3, status);
	        rideStmt.executeUpdate();

	        // Retrieve generated ride ID
	        ResultSet rs1 = rideStmt.getGeneratedKeys();
	        int rideId = -1;
	        if (rs1.next()) {
	            rideId = rs1.getInt(1);
	        } else {
	            System.out.println("Error: Ride ID retrieval failed.");
	            return null;
	        }
	        
	        PreparedStatement ps=conn.prepareStatement("select id from travel_routes where (pickup_loc=? and destination_loc=?) or(pickup_loc=? and destination_loc=?)");
	        ps.setInt(1, plocation);
	        ps.setInt(2, dlocation);
	        ps.setInt(3, dlocation);
	        ps.setInt(4, plocation);
	        int route_id=-1;
	        ResultSet rs2=ps.executeQuery();
	        if(rs2.next()) {
	        	route_id=rs2.getInt(1);
	        }

	        PreparedStatement rir=conn.prepareStatement("insert into ride_in_routes (ride_id,route_id) values (?,?)");
	        rir.setInt(1, rideId);
	        rir.setInt(2, route_id);
	        rir.executeUpdate();
	        // Prompt user for payment mode
	        

	        // Insert into `transactions` table
	        PreparedStatement tranStmt = conn.prepareStatement(
	            "INSERT INTO transactions (actual_fair, final_fair, discount, tmode, date) VALUES (?, ?, ?, ?, ?);",  // ✅ Updated column names
	            PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        tranStmt.setDouble(1, actual);
	        tranStmt.setDouble(2, fin);
	        tranStmt.setDouble(3, dis);
	        tranStmt.setString(4, payMode);
	        tranStmt.setTimestamp(5, t);
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
	            "INSERT INTO user_ride (user_id, ride_id,rType,date) VALUES (?, ?,?,?);"  // ✅ Removed `date` column (handled in default timestamp)
	        );
	        userRideStmt.setInt(1, userId);
	        userRideStmt.setInt(2, rideId);
	        if(status.equals("scheduled")) {
	        	userRideStmt.setString(3, status);
	        }
	        else {
	        	userRideStmt.setString(3, "normal");
			}
	        userRideStmt.setTimestamp(4, t);
	        userRideStmt.executeUpdate();
	        
	        
	        PreparedStatement rideStat=conn.prepareStatement("insert into RStatus (r_id,status) "
	        		+ "values (?,?)");
	        
	        rideStat.setInt(1, rideId);
	        if(status.equals("scheduled"))
	        rideStat.setString(2, status);
	        else {
				rideStat.setString(2,"Booked");
			}
	        rideStat.executeUpdate();
	        
	        PreparedStatement trs=conn.prepareStatement("insert into transaction_ride (ride_id,t_id) values(?,?)");
	        trs.setInt(1, rideId);
	        trs.setInt(2, tranId);
	        trs.executeUpdate();
	        
	        PreparedStatement tranStat=conn.prepareStatement("insert into TStatus (t_id,tStatus) values(?,?)");
	        tranStat.setInt(1, tranId);
	        tranStat.setString(2, tStatus);
	        tranStat.executeUpdate();
	        
	        PreparedStatement rev=conn.prepareStatement("insert into reviews (user_id,ride_id,rating,review) "
	        		+ "values(?,?,?,?)");
	        rev.setInt(1, userId);
	        rev.setInt(2, rideId);
	        rev.setInt(3, rating);
	        rev.setString(4, review);
	        
	        rev.executeUpdate();
	        
	        System.out.println("Ride added successfully!");

	        HashMap<String, Double>hm=new HashMap<>();
	        hm.put(t.toString(),fin);
	        return hm;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
