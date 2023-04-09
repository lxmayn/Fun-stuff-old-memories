package com.timeline.models;

public class User {
	private String username;
	private String password;
	private int admin;

	/* To do: error handling */
	public User(String username, String password, int admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

	// Constructor for admin purposes
	public User(String username, int admin) {
		this.username = username;
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {

		if (admin == 1) {
			return true;
		} else {
			return false;
		}

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAdmin(boolean isAdmin) {

		if (isAdmin) {
			this.admin = 1;
		} else {
			this.admin = 0;
		}
	}

}
