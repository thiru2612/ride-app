package com.rideapp.api.entity;

import java.sql.Timestamp;

public class TransactionHistory {
	private int transaction_id;
	private int ride_id;
	private String transaction_Mode;
	private String transaction_Status;
	private double Amount;
	private Timestamp Date;
	
	public TransactionHistory() {}
	
	public TransactionHistory(int transaction_id, int ride_id, String transaction_Mode, String transaction_Status,
			double amount, Timestamp date) {
		super();
		this.transaction_id = transaction_id;
		this.ride_id = ride_id;
		this.transaction_Mode = transaction_Mode;
		this.transaction_Status = transaction_Status;
		Amount = amount;
		Date = date;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getRide_id() {
		return ride_id;
	}
	public void setRide_id(int ride_id) {
		this.ride_id = ride_id;
	}
	public String getTransaction_Mode() {
		return transaction_Mode;
	}
	public void setTransaction_Mode(String transaction_Mode) {
		this.transaction_Mode = transaction_Mode;
	}
	public String getTransaction_Status() {
		return transaction_Status;
	}
	public void setTransaction_Status(String transaction_Status) {
		this.transaction_Status = transaction_Status;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public Timestamp getDate() {
		return Date;
	}
	public void setDate(Timestamp date) {
		Date = date;
	}
	
}
