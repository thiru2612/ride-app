package com.ride.repository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.ride.entity.User;

public class UserRepository {

	public static HashSet<User>users=new HashSet<>();
	
	static {
		User u1=new User("admin","admin","123456","admin");
		User u2=new User("user1","user1","123456","user");
		User u3=new User("user2","user2","123456","user");
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
	}
	
	public User login(String username,String password) {
		List<User> userList=users.stream()
                .filter(user->user.getUsername().equals(username)&&user.getPassword().equals(password))
                .collect(Collectors.toList());
		if(userList.isEmpty()) {
			return null;
		}
		return userList.get(0);
	}
	
	public boolean createUser(String username,String password,String contact) {
		User u=new User(username,password,contact,"user");
		return users.add(u);
	}
}
