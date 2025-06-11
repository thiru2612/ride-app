package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.rideapp.api.db.DatabaseConnection;


public class CancelRideService {
	public String cancelRide(int userId,Timestamp t) {
		try (Connection conn=DatabaseConnection.getConnection()){
			PreparedStatement st1=conn.prepareStatement("select ride_id from user_ride where user_id =? and date=?");
			st1.setInt(1,userId);
			st1.setTimestamp(2, t);
			ResultSet rs1=st1.executeQuery();
			System.out.println("UserId: "+userId+" time: "+t);
			int ride_id=-1;
			if(rs1.next()) {
				ride_id=rs1.getInt("ride_id");
			}
			if (ride_id != -1) {
			    PreparedStatement st2 = conn.prepareStatement("UPDATE RStatus SET status = 'Cancelled' WHERE r_id = ?");
			    st2.setInt(1, ride_id);
			    st2.executeUpdate();

			    PreparedStatement st3=conn.prepareStatement("select t_id from transaction_ride where ride_id = ?");
			    st3.setInt(1, ride_id);
			    ResultSet rs3=st3.executeQuery();
			    
			    int tran_id;
			    if(rs3.next()) {
			    	tran_id=rs3.getInt(1);
			    	PreparedStatement st4 = conn.prepareStatement("UPDATE transactions SET final_fair = final_fair * 0.2 WHERE id = ?");
			    	st4.setInt(1, tran_id);
			    	st4.executeUpdate();
			    }
			    
			    
			    
			    return new String("Cancelled pre booking on "+t+" and 80 % refunded\n");
			} else {
			    return new String("Error: No ride found for cancellation.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
