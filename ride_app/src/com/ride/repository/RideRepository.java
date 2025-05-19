package com.ride.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ride.entity.Ride;
import com.ride.entity.User;

public class RideRepository {
	
	public static ArrayList<Ride> rides=new ArrayList<>();
	
	static {
			Ride r1=new Ride("Car",10.0, "Chennai", new User("Thiru","ssaca","123","user"));
//			Ride r2=new Ride("Bike", "5 km", "Mumbai", new User("Ravi")));
			rides.add(r1);
//			rides.add(r2);
	}
	public boolean addRide(Ride ride) {
		
		try {
			
			rides.add(ride);
			rides.sort((r1, r2) -> r2.getD().compareTo(r1.getD()));
		} catch (Exception e) {
			return false;
		}
		return true;
    }

    // Method to view rides without user details
    public List<String> viewRides() {
        List<String> rideDetails = new ArrayList<>();
        
        for (Ride ride : rides) {
            rideDetails.add("Mode: " + ride.getMode() + ", Distance: " + ride.getDistance() + "Km , Location: " + ride.getLocation() +" Price :"+ride.getPrice()+ ", Date: " + ride.getD());
        }
        
        return rideDetails;
    }
    public List<String> viewRides(User u) {
	    List<Ride> rideList=rides.stream()
	            .filter(r->r.getU().getUsername().equals(u.getUsername()))
	            .collect(Collectors.toList());
	    List<String> rideDetails = new ArrayList<>();
        if(rideList.isEmpty()) {
        	return null;
        }
        for (Ride ride : rideList) {
            rideDetails.add("Mode: " + ride.getMode() + ", Distance: " + ride.getDistance() + "Km, Location: " + ride.getLocation() + ", Date: " + ride.getD()+"Price :"+ride.getPrice());
        }
        
        return rideDetails;
//    &&user.getPassword().equals(password)
    }
    
    
	
}