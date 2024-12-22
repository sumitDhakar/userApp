package com.user.app.exception;

public class UnauthorizedException extends RuntimeException{

	public UnauthorizedException() {
		super("Resource Already Exists ");
	}
	

	public UnauthorizedException(String msg) {
		super(msg);
	}
}
