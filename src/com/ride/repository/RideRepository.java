package com.ride.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ride.db.DatabaseConnection;
import com.ride.entity.User;
public class RideRepository {
	

	public boolean addRide(String mode,String location,Double distance,Double price,User u) {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement stmt=conn.prepareStatement("Insert into rides (mode,distance,location,price,user_id) values("
					+ "?,?,?,?,?)");
			stmt.setString(1, mode);
			stmt.setDouble(2, distance);
			stmt.setString(3, location);
			stmt.setDouble(4, price);
			stmt.setInt(5, u.getId());
			
			stmt.executeUpdate();
			PreparedStatement p=conn.prepareStatement("UPDATE users SET last_booked_date = CURRENT_TIMESTAMP  where username=?");
			
			p.setString(1, u.getUsername());
			
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    // Method to view rides without user details
    public List<String> viewRides() {
        List<String> rideDetails = new ArrayList<>();
        
        try(Connection conn=DatabaseConnection.getConnection()){
        	PreparedStatement p=conn.prepareStatement("select mode,distance,price ,date from rides order by 4 desc");
        	ResultSet rs=p.executeQuery();
        	
        	while(rs.next()) {
        		rideDetails.add("Mode: "+rs.getString("mode")+" Distance: "+rs.getString("distance")+" Price: "+rs.getString("price")+" Date: "+rs.getString("date"));
        	}
        }
        catch (SQLException e) {
        	e.printStackTrace();
		}
        return rideDetails;
    }
    
    
    public List<String> viewRides(User u) {
    	
    	List<String> rideDetails = new ArrayList<>();
    	try (Connection conn=DatabaseConnection.getConnection();
    			PreparedStatement stmt = conn.prepareStatement("SELECT r.mode, r.distance, r.price, r.date FROM rides r INNER JOIN users u ON r.user_id = u.id WHERE u.username = ?")){
    		stmt.setString(1, u.getUsername());
    		
    		ResultSet rs=stmt.executeQuery();
    		
    		while(rs.next()) {
    			rideDetails.add("Mode: "+rs.getString("mode")+" Distance: "+rs.getString("distance")+" Price: "+rs.getString("price")+" Date: "+rs.getString("date"));
    		}
    		return rideDetails;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
}