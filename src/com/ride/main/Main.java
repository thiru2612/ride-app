package com.ride.main;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import com.ride.entity.User;
import com.ride.service.RideService;
import com.ride.service.ShowRide;
import com.ride.service.UserService;
import com.ride.service.preBookService;
import com.ride.service.reviewService;
import com.ride.service.transactionService;

public class Main {

	private static Scanner sc=new Scanner(System.in);
	static UserService us=new UserService();
	static RideService rs=new RideService();
	public static void main(String[] args) {
		Main main=new Main();
		while (true) {
			System.out.println("Welcome to the ridy app");
			System.out.println("Please login to continue");
			System.out.println("New user(y/n)");
			String euser=sc.next().toLowerCase();
			
			if(euser.equals("y")) {
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
					System.out.println("\nLogin failed.\nPlease Try again");
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
					break;
				default:
					System.out.println("Invalid choice");
			}
		}
	}
	private void initUser(User u){
		boolean flag=true;
		while (flag) {
			System.out.println("\n1.Book a ride");
			System.out.println("2.View Ride history");
			System.out.println("3.Prebook Section");
			System.out.println("4.Transaction history");
			System.out.println("5.Exit");
			System.out.println("Enter your choice:");
			int choice=sc.nextInt();
			switch(choice){
			case 1:
				bookRide(u,null);
				break;
			case 2:
				viewRide(u);
				break;
			case 3:
				preBook(u);
				break;
			case 4:
				transactionService.viewT(u);
				break;
			case 5:
				System.out.println("Exiting...");
				flag=false;
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
	}
	public void preBook(User u) {
		preBookService ps=new preBookService();
		boolean flag=true;
		while(flag) {
			
			System.out.println("Enter your choice:\n1.Preebook a ride\n2.view my prebooking\n3.Cancel prebooking\n4.Go back to menu");
			int choice=sc.nextInt();
			switch(choice){
			case 1:
				ps.preBookRide(u);
				break;
			case 2:
				ps.viewPreBook(u);
				break;
			case 3:
				ps.cancelRide(u);
				break;
			case 4:
				System.out.println("Exiting...");
				flag=false;
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
		return;
	}

	private void createUser(){
		System.out.println("Creating a user account");
		System.out.println("Enter username:");
		String username=sc.next();
		System.out.println("Enter password:");
		String password=sc.next();
		System.out.println("Enter contact number:");
		String contactNumber=sc.next();
		System.out.println("Enter your age:");
		int age=sc.nextInt();
		if(us.createUser(username, password, contactNumber,age)){
			System.out.println("User account created successfully");
		}
		else{
			System.out.println("User account creation failed");
		}
	}
	static List<String>l;
	public void bookRide(User u,Timestamp t) {
		System.out.println("\nEnter mode of travel:\n1.Bike\n2.Auto\n3.Car");
		  sc.nextLine();
		String choice=sc.nextLine();
		String mode="";
		Double price;
		switch (choice) {
		case "1": 
		case "bike": 
		case "Bike": 
			price=50.0;
			mode=new String("Bike");
			break;
		case "2": 
		case "Auto": 
		case "auto": 
			price=75.0;
			mode=new String("Auto");
			break;
		case "3": 
		case "Car": 
		case "car": 
			price=100.0;
			mode=new String("Car");
			break;
		default:
			System.out.println("invalid input");
			return;
			
		}
		System.out.println("We provide service in the below location:");
		ShowRide.showServiceArea();
		System.out.println("Enter pickup location:");
		int plocation=sc.nextInt();
		System.out.println("Enter destination location:");
		int dlocation=sc.nextInt();
		if((plocation<0||plocation>11)||(dlocation<0||dlocation>11)||(dlocation==plocation)) {
			System.out.println("Invalid choice");
			return;
		}
		reviewService res=rs.bookRide(mode, plocation,dlocation,price, u,t);
		
		
		
		if(res==null) {
			System.out.println("booking process failed..");
		}
		else {
			reviewService.addReview(res.getrId(), res.getuId(),res.gettId());
		}
	}
	
	public void viewRide(User u) {
		System.out.println("Showing your ride history");
		
		l=rs.viewRide(u);
		if(l==null||l.isEmpty()) {
			System.out.println("You have not booked a ride yet...\n");
			return;
		}
		for(String s:l) {
			System.out.println(s);
		}
	}

	public void viewRide() {
		System.out.println("\nShowing Customer ride history:\n");
		l=rs.viewRide();
		if(l==null||l.isEmpty()) {
			System.out.println("nobody booked a ride yet...\n");
			return;
		}
		for(String s:l) {
			System.out.println(s);
		}
		System.out.println();
	}
}
