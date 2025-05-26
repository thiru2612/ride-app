package com.ride.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.ride.db.DatabaseConnection;

public class reviewService {
	
	private int rId;
	private int uId;
	private int tId;
	public reviewService(int rId, int uId,int tId) {
		super();
		this.rId = rId;
		this.uId = uId;
	}
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public static boolean addReview(int ride_id, int user_id,int tranId) {
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        // Verify user exists
	        PreparedStatement userCheckStmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?");
	        userCheckStmt.setInt(1, user_id);
	        ResultSet userCheck = userCheckStmt.executeQuery();
	        if (!userCheck.next()) {
	            System.out.println("Error: User ID " + user_id + " does not exist.");
	            return false;
	        }

	        // Verify ride exists
	        PreparedStatement rideCheckStmt = conn.prepareStatement("SELECT id FROM rides WHERE id = ?");
	        rideCheckStmt.setInt(1, ride_id);
	        ResultSet rideCheck = rideCheckStmt.executeQuery();
	        if (!rideCheck.next()) {
	            System.out.println("Error: Ride ID " + ride_id + " does not exist.");
	            return false;
	        }

	        // Proceed with review insertion
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Kindly give review for later improvements");
	        System.out.println("Rate on the scale of 1 - 10:");
	        int rating = sc.nextInt();
	        System.out.println("Enter any comment:");
	        sc.nextLine();  
	        String comment = sc.nextLine();

	        PreparedStatement p = conn.prepareStatement("INSERT INTO reviews (ride_id, user_id, rating, review) VALUES (?, ?, ?, ?)");
	        p.setInt(1, ride_id);
	        p.setInt(2, user_id);
	        p.setInt(3, rating);
	        p.setString(4, comment);
	        
	        p.executeUpdate();
	        System.out.println("Review added successfully!");
	        payService.getStatus(tranId);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
