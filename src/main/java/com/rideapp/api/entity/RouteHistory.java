package com.rideapp.api.entity;

import java.sql.Timestamp;

public class RouteHistory {
	private int routeId;
	private String start;
	private String end;
	private double distance;
	private int userId;
	private String mode;
	private Timestamp date;
	
	
	public RouteHistory() {}
	public RouteHistory(int routeId, int userId, String start, String end, double distance, String mode,Timestamp t) {
		super();
		this.routeId = routeId;
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.userId = userId;
		this.mode = mode;
		this.date=t;
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}