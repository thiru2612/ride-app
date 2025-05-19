package com.ride.service;

import java.util.List;

import com.ride.entity.Ride;
import com.ride.entity.User;
import com.ride.repository.RideRepository;

public class RideService {

	static RideRepository rr=new RideRepository();
	public List<String> viewRide(){
		return rr.viewRides();
	}
	
	public Ride bookRide(String mode,String location,Double distance,User u) {
		Ride r=new Ride(mode, distance, location, u);
		if( !rr.addRide(r)) {
			return null;
		}
		return r;
	}
	
	public List<String> viewRide(User u){
		return rr.viewRides(u);
	}
}
