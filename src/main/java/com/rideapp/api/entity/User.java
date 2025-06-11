package com.rideapp.api.entity;

public class User {
    private int userId;
    private String username;
    private String password;
    private String contact; // ✅ Ensure this field exists
    private int age;
    private String role;

    // ✅ Default constructor required for Gson
    public User() {}

    public User(int userId, String username, String role, int age) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.age = age;
    }
    public User(int userId, String username, int age,String contact) {
    	this.userId = userId;
    	this.username = username;
    	this.contact=contact;
    	this.age = age;
    }

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// ✅ Getters & Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContact() { return contact; }  // ✅ Ensure this method exists
    public void setContact(String contact) { this.contact = contact; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }}
