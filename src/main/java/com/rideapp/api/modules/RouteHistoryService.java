package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rideapp.api.db.DatabaseConnection;
import com.rideapp.api.entity.RouteHistory;

public class RouteHistoryService {
	public List<RouteHistory>getRouteHistory(int routeId){
		List<RouteHistory> rideHistoryList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {

        	PreparedStatement stmt = conn.prepareStatement("SELECT rir.route_id,ur.user_id,r.rMode, r.date, \r\n"
    				+ "       lp.location AS pickup, \r\n"
    				+ "       ld.location AS destination, \r\n"
    				+ "       tr.distance \r\n"
//    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
    				+ "FROM rides r \r\n"
    				+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
    				+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
    				+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
    				+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
//    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
//    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
//    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
//    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
    				+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
    				+ "WHERE route_id=? \r\n"
    				+ "ORDER BY r.date DESC;");

            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RouteHistory ride = new RouteHistory(
                    rs.getInt("route_id"),
                    rs.getInt("user_id"),
                    rs.getString("pickup"),
                    rs.getString("destination"),
                    rs.getDouble("distance"),	
                    rs.getString("rMode"),
                    rs.getTimestamp("date")
                );
                rideHistoryList.add(ride);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rideHistoryList;
	}
	public List<RouteHistory>getRouteHistory(int routeId,String mode){
		List<RouteHistory> rideHistoryList = new ArrayList<>();
		
		try (Connection conn = DatabaseConnection.getConnection()) {
			
			PreparedStatement stmt = conn.prepareStatement("SELECT rir.route_id,ur.user_id,r.rMode, r.date, \r\n"
					+ "       lp.location AS pickup, \r\n"
					+ "       ld.location AS destination, \r\n"
					+ "       tr.distance \r\n"
//    				+ "       COALESCE(pr.rating, 'No Rating') AS review \r\n"
					+ "FROM rides r \r\n"
					+ "INNER JOIN ride_in_routes rir ON r.id = rir.ride_id \r\n"
					+ "INNER JOIN travel_routes tr ON rir.route_id = tr.id\r\n"
					+ "INNER JOIN locations lp ON tr.pickup_loc = lp.id \r\n"
					+ "INNER JOIN locations ld ON tr.destination_loc = ld.id \r\n"
//    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = r.id \r\n"
//    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
//    				+ "inner join RStatus ts on r.id=ts.r_id \r\n"
//    				+ "LEFT JOIN reviews pr ON r.id = pr.ride_id \r\n"
					+ "INNER JOIN user_ride ur ON r.id = ur.ride_id \r\n"
					+ "WHERE route_id=? and rMode=? \r\n"
					+ "ORDER BY r.date DESC;");
			
			stmt.setInt(1, routeId);
			stmt.setString(2, mode);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				RouteHistory ride = new RouteHistory(
						rs.getInt("route_id"),
						rs.getInt("user_id"),
						rs.getString("pickup"),
						rs.getString("destination"),
						rs.getDouble("distance"),	
						rs.getString("rMode"),
						rs.getTimestamp("date")
						);
				rideHistoryList.add(ride);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rideHistoryList;
	}
}
