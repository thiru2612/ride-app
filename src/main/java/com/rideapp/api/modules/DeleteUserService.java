package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.rideapp.api.db.DatabaseConnection;

public class DeleteUserService {
	public boolean deleteUser(int userId) {
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        // ✅ Step 1: Delete related records in `reviews` table
	        PreparedStatement delReviews = conn.prepareStatement("DELETE FROM reviews WHERE user_id = ?");
	        delReviews.setInt(1, userId);
	        delReviews.executeUpdate();
	        
	        PreparedStatement delUserRide = conn.prepareStatement("DELETE FROM user_ride WHERE user_id = ?");
	        delUserRide.setInt(1, userId);
	        delUserRide.executeUpdate();
	        
	        
	        PreparedStatement delUserStatus = conn.prepareStatement("DELETE FROM user_status WHERE user_id = ?");
	        delUserStatus.setInt(1, userId);
	        delUserStatus.executeUpdate();

	        
	        
	        // ✅ Step 2: Delete user from `users` table
	        PreparedStatement delUser = conn.prepareStatement("DELETE FROM users WHERE id = ?");
	        delUser.setInt(1, userId);
	        return delUser.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
