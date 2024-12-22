package com.user.app.exception;

public class InvalidCredientials extends RuntimeException{

	public InvalidCredientials() {
		super("Invalid Credentials");
	}
	

	public InvalidCredientials(String msg) {
		super(msg);
	}
}
