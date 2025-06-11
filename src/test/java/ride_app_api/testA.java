package ride_app_api;

import com.rideapp.api.modules.CreateUser;

public class testA {
	public static void main(String args[]) {
		CreateUser cu=new CreateUser();
		int id=cu.createUser("riyas", "sam", "8876545670", 2);
		System.out.println(id);
	}
}