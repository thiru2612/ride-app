package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rideapp.api.db.DatabaseConnection;
import com.rideapp.api.entity.RideHistory;

public class RideHistoryService {
    public List<RideHistory> getRideHistory(int userId) {
        List<RideHistory> rideHistoryList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

        	PreparedStatement stmt = conn.prepareStatement("SELECT r.id,r.rMode,rir.route_id, r.date, ts.status, \r\n"
    				+ "       COALESCE(t.final_fair, 0) AS final, \r\n"
    				+ "       COALESCE(t.discount, 0) AS discount, \r\n"
    				+ "       lp.location AS pickup, \r\n"
    				+ "       ld.location AS destination, \r\n"
    				+ "       tr.distance, \r\n"
    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
    				+ "FROM rides r \r\n"
    				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
    				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
    				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
    				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
    				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
    				+ "WHERE ur.user_id=? \r\n"
    				+ "ORDER BY r.date DESC;");

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RideHistory ride = new RideHistory(
                    rs.getInt("id"),
                    userId,
                    rs.getInt("route_id"),
                    rs.getString("pickup"),
                    rs.getString("destination"),
                    rs.getTimestamp("date"),
                    rs.getDouble("final"),
                    rs.getDouble("discount"),
                    rs.getString("rMode"),
                    rs.getString("review"),
                    rs.getString("status")
                );
                rideHistoryList.add(ride);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rideHistoryList;
    }
    public List<RideHistory> getRideStatus(int userId,String status) {
    	List<RideHistory> rideHistoryList = new ArrayList<>();
    	
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		
    		PreparedStatement stmt = conn.prepareStatement("SELECT r.id,r.rMode,rir.route_id, r.date, ts.status, \r\n"
				+ "       COALESCE(t.final_fair, 0) AS final, \r\n" 
				+ "       lp.location AS pickup, \r\n"
				+ "       ld.location AS destination, \r\n"
				+ "       tr.distance, \r\n"
				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
				+ "FROM rides r \r\n"
				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
				+ "WHERE ur.user_id=? and ts.status=?\r\n"
				+ "ORDER BY r.date DESC;");
    		
    		stmt.setInt(1, userId);
    		stmt.setString(2,status);
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			RideHistory ride = new RideHistory(
    					rs.getInt("id"),
    					userId,
    					rs.getInt("route_id"),
    					rs.getString("pickup"),
    					rs.getString("destination"),
    					rs.getTimestamp("date"),
    					rs.getDouble("final"),
    					rs.getString("rMode"),
    					rs.getString("review"),
    					rs.getString("status")
    					);
    			rideHistoryList.add(ride);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return rideHistoryList;
    }
    public List<RideHistory> getRideStatusDate(int userId,String status,Date t) {
    	List<RideHistory> rideHistoryList = new ArrayList<>();
    	
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		
    		PreparedStatement stmt = conn.prepareStatement("SELECT r.id,r.rMode,rir.route_id, r.date, ts.status, \r\n"
    				+ "       COALESCE(t.final_fair, 0) AS final, \r\n" 
    				+ "       lp.location AS pickup, \r\n"
    				+ "       ld.location AS destination, \r\n"
    				+ "       tr.distance, \r\n"
    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
    				+ "FROM rides r \r\n"
    				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
    				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
    				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
    				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
    				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
    				+ "WHERE ur.user_id=? and ts.status=? and DATE(r.date)=? \r\n"
    				+ "ORDER BY r.date DESC;");
    		
    		stmt.setInt(1, userId);
    		stmt.setString(2,status);
    		stmt.setDate(3, t);
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			RideHistory ride = new RideHistory(
    					rs.getInt("id"),
    					userId,
    					rs.getInt("route_id"),
    					rs.getString("pickup"),
    					rs.getString("destination"),
    					rs.getTimestamp("date"),
    					rs.getDouble("final"),
    					rs.getString("rMode"),
    					rs.getString("review"),
    					rs.getString("status")
    					);
    			rideHistoryList.add(ride);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return rideHistoryList;
    }
    public List<RideHistory> getRideDate(int userId,Date t) {
    	List<RideHistory> rideHistoryList = new ArrayList<>();
    	
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		
    		PreparedStatement stmt = conn.prepareStatement("SELECT r.id,r.rMode,rir.route_id, r.date, ts.status, \r\n"
    				+ "       COALESCE(t.final_fair, 0) AS final, \r\n" 
    				+ "       lp.location AS pickup, \r\n"
    				+ "       ld.location AS destination, \r\n"
    				+ "       tr.distance, \r\n"
    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
    				+ "FROM rides r \r\n"
    				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
    				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
    				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
    				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
    				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
    				+ "WHERE ur.user_id=? and date(r.date)=? \r\n"
    				+ "ORDER BY r.date DESC;");
    		
    		stmt.setInt(1, userId);
    		stmt.setDate(2, t);
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			RideHistory ride = new RideHistory(
    					rs.getInt("id"),
    					userId,
    					rs.getInt("route_id"),
    					rs.getString("pickup"),
    					rs.getString("destination"),
    					rs.getTimestamp("date"),
    					rs.getDouble("final"),
    					rs.getString("rMode"),
    					rs.getString("review"),
    					rs.getString("status")
    					);
    			rideHistoryList.add(ride);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return rideHistoryList;
    }
    public List<RideHistory> getRideMode(int userId,String mode) {
    	List<RideHistory> rideHistoryList = new ArrayList<>();
    	
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		
    		PreparedStatement stmt = conn.prepareStatement("SELECT r.id,r.rMode,rir.route_id, r.date, ts.status, \r\n"
    				+ "       COALESCE(t.final_fair, 0) AS final, \r\n" 
    				+ "       lp.location AS pickup, \r\n"
    				+ "       ld.location AS destination, \r\n"
    				+ "       tr.distance, \r\n"
    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
    				+ "FROM rides r \r\n"
    				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
    				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
    				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
    				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
    				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
    				+ "WHERE ur.user_id=? and r.rMode=? \r\n"
    				+ "ORDER BY r.date DESC;");
    		
    		stmt.setInt(1, userId);
    		stmt.setString(2, mode);
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			RideHistory ride = new RideHistory(
    					rs.getInt("id"),
    					userId,
    					rs.getInt("route_id"),
    					rs.getString("pickup"),
    					rs.getString("destination"),
    					rs.getTimestamp("date"),
    					rs.getDouble("final"),
    					rs.getString("rMode"),
    					rs.getString("review"),
    					rs.getString("status")
    					);
    			rideHistoryList.add(ride);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return rideHistoryList;
    }
}