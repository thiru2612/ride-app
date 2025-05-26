package com.ride.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.ride.db.DatabaseConnection;
public class payService {

	public static void getStatus(int tranId) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Have you paid:\n1.yes\n2.no");
		int choice=sc.nextInt();
		
		try (Connection conn=DatabaseConnection.getConnection()){
			boolean flag=true;
			while(flag) {
				
				switch (choice) {
					case 1:
						PreparedStatement tran=conn.prepareStatement("update transactions set tStatus='Success'");
						tran.executeUpdate();
						System.out.println("Transaction successful.\n");
						flag=false;
						break;
					case 2:
						PreparedStatement tran2=conn.prepareStatement("update transactions set tStatus='Failed'");
						System.out.println("Transaction failed.\n");
						tran2.executeUpdate();
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + choice);
				} 
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
