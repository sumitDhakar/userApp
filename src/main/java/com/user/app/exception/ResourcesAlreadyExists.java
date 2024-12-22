package com.user.app.exception;

public class ResourcesAlreadyExists extends RuntimeException{

	public ResourcesAlreadyExists() {
		super("Resource Already Exists ");
	}
	

	public ResourcesAlreadyExists(String msg) {
		super(msg);
	}
}
