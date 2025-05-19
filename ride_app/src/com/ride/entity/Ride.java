package com.ride.entity;

import java.util.Date;

public class Ride {

	private String mode;
	private Double distance;
	private String location;
	Date d;
	private User u;
	private Double price; 


	
	public Ride(String mode, Double distance, String location, User u) {
		super();
		this.mode = mode;
		this.distance = distance;
		this.location = location;
		this.u = u;
		if(mode.equals("car")) {
			this.price = distance*20;
		}
		else if(mode.equals("bike")){
			this.price = distance*10;
		}
		else {
			this.price = distance*15;
		}
		d=new Date();
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Double getDistance() {
		return distance;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	@Override
	public String toString() {
		return "Ride [mode=" + mode + ", distance=" + distance + ", location=" + location + ", u=" + u + "]";
	}
	
	
}
