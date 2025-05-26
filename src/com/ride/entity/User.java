package com.ride.entity;

public class User {

	private int id;
	private String username;
	private int age;
	private String role;
	private String usage;
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User(String username,String role,int age,int id,String usage ) {
		super();
		this.username = username;
		this.role = role;
		this.age=age;
		this.id=id;
		this.usage=usage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}	
}