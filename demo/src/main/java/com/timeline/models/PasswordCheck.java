package com.timeline.models;

import java.util.stream.Stream;

public class PasswordCheck {

	public PasswordCheck() {

	}

	// This class is used to check the validity of the password according to
	// requirements

	public boolean passwordCheck(String password) {
		boolean number = false;
		boolean upper = false;
		boolean lower = false;
		char p;
		if (password.length() > 5) {
			for (int i = 0; i < password.length(); i++) {
				p = password.charAt(i);
				if (Character.isDigit(p)) {
					number = true;
				} else if (Character.isUpperCase(p)) {
					upper = true;
				} else if (Character.isLowerCase(p)) {
					lower = true;
				}
			}
			if (number && upper && lower) {
				return true;
			}
		}
		return false;
	}
}