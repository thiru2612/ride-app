package com.ride.main;
import java.util.List;
import java.util.Scanner;

import com.ride.entity.Ride;
import com.ride.entity.User;
import com.ride.service.RideService;
import com.ride.service.UserService;

public class Main {

	private static Scanner sc=new Scanner(System.in);
	static UserService us=new UserService();
	static RideService rs=new RideService();
	public static void main(String[] args) {
		Main main=new Main();
		while (true) {
			System.out.println("Welcome to the ridy app");
			System.out.println("Please login to continue");
			System.out.println("Exisiting user/New user");
			String euser=sc.next();
			
			if(euser.equals("new")) {
				main.createUser();
			}
			else {
				System.out.println("Enter your username:");
				String username=sc.next();
				System.out.println("Enter your password:");
				String password=sc.next();
				
				User user=us.login(username, password);
				
				if(user!=null&&user.getRole().equals("admin")){
					
					System.out.println("\nLogin successful");
					main.initAdmin();
				}
				else if(user!=null&&user.getRole().equals("user")){
					
					System.out.println("\nLogin successful");
					main.initUser(user);
				}
				else{
					System.out.println("\nLogin failed\nNavigating to login page");
				}
			}
		}
			
	}

	private void initAdmin(){
		boolean flag=true;
		while(flag){
			System.out.println("1.view ride history");
			System.out.println("2.Exit");

			System.out.println("Enter your choice:");
			int choice=sc.nextInt();

			switch(choice){
				case 1:
					viewRide();
					break;
				case 2:
					System.out.println("Exiting...");
					flag=false;
				default:
					System.out.println("Invalid choice");
			}
		}
	}

	private void createUser(){
		System.out.println("Creating a user account");
		System.out.println("Enter username:");
		String username=sc.next();
		System.out.println("Enter password:");
		String password=sc.next();
		System.out.println("Enter contact number:");
		String contactNumber=sc.next();
		if(us.createUser(username, password, contactNumber)){
			System.out.println("User account created successfully");
		}
		else{
			System.out.println("User account creation failed");
		}
	}
	static List<String>l;
	private void initUser(User u){
		boolean flag=true;
		while (flag) {
			
			System.out.println("1.Book a ride");
			System.out.println("2.View Ride history");
			System.out.println("3.Exit");
			System.out.println("Enter your choice:");
			int choice=sc.nextInt();
//			sc.next();
			switch(choice){
				case 1:
					bookRide(u);
					break;
				case 2:
					viewRide(u);
					break;
				case 3:
					System.out.println("Exiting...");
					flag=false;
					break;
				default:
				System.out.println("Invalid choice");
			}
		}
	}

	public void bookRide(User u) {
		System.out.println("\nEnter mode of travel:\n1.Bike\n2.Auto\n3.Car");
		  sc.nextLine();
		String choice=sc.nextLine();
		String mode="";
		switch (choice) {
		case "1": 
		case "bike": 
		case "Bike": 
			mode=new String("Bike");
			break;
		case "2": 
		case "Auto": 
		case "auto": 
			mode=new String("Auto");
			break;
		case "3": 
		case "Car": 
		case "car": 
			mode=new String("Car");
			break;
		default:
			System.out.println("invalid input");
			
		}

		System.out.println("Enter pickup location:");
		String location=sc.nextLine();
		System.out.println("Enter distance:");
		Double dis=sc.nextDouble();
		
		Ride r=rs.bookRide(mode, location, dis, u);
		
		if(r==null) {
			System.out.println("booking process failed..");
		}
		else {
			System.out.println("Booking complete.\nGenerating invoice..");
			System.out.println("\tUser:"+r.getU().getUsername()+"\n\tdistance:"+r.getDistance());
			System.out.println("\tmode:"+r.getMode()+"\n\tPrice:"+r.getPrice());
		}
	}
	
	public void viewRide(User u) {
		System.out.println("Showing your ride history");
		
		l=rs.viewRide(u);
		if(l==null) {
			System.out.println("You have not booked a ride yet...\n");
			return;
		}
		for(String s:l) {
			System.out.println(s);
		}
	}

	public void viewRide() {
		System.out.println("Showing Customer ride history");
		
		l=rs.viewRide();
		if(l==null) {
			System.out.println("You have not booked a ride yet...\n");
			return;
		}
		for(String s:l) {
			System.out.println(s);
		}
	}
}
