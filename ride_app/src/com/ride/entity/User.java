package com.ride.entity;

import java.util.Objects;

public class User {

	private String username;
	private String password;
	private String contact;
	private String role;
//	private String
	public User(String username, String password, String contact, String role) {
		super();
		this.username = username;
		this.password = password;
		this.contact = contact;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", contact=" + contact + ", role=" + role
				+ "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(contact, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(contact, other.contact) && Objects.equals(username, other.username);
	}
	
	
}
