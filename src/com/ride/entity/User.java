package com.ride.entity;

public class User {

	private int id;
	private String username;
	private int age;
	private String role;
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
	public User(String username,String role,int age,int id) {
		super();
		this.username = username;
		this.role = role;
		this.age=age;
		this.id=id;
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