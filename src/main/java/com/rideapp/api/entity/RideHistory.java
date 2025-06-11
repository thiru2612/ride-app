package com.rideapp.api.entity;

import java.sql.Timestamp;

public class RideHistory {
    private int rideId;
    private int userId;
    private int routeId;
    private String startLocation;
    private String endLocation;
    private Timestamp dateTime;
    private String status;
    private double fare;
    private double discount;
    private String mode;
    private String review;

    
    
    public RideHistory(int rideId, int userId,int routeId, String startLocation, String endLocation, Timestamp dateTime,
			double fare, double discount, String mode, String review, String status) {
		super();
		this.rideId = rideId;
		this.userId = userId;
		this.routeId=routeId;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.dateTime = dateTime;
		this.status = status;
		this.fare = fare;
		this.discount = discount;
		this.mode = mode;
		this.review = review;
	}
    public RideHistory(int rideId, int userId,int routeId, String startLocation, String endLocation, Timestamp dateTime,
    		double fare,  String mode, String review, String status) {
    	super();
    	this.rideId = rideId;
    	this.userId = userId;
    	this.routeId=routeId;
    	this.startLocation = startLocation;
    	this.endLocation = endLocation;
    	this.dateTime = dateTime;
    	this.status = status;
    	this.fare = fare;
    	this.mode = mode;
    	this.review = review;
    }

	// ✅ Default constructor required for Gson
    public RideHistory() {}

	public int getRideId() {
		return rideId;
	}

	public void setRideId(int rideId) {
		this.rideId = rideId;
	}

	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}