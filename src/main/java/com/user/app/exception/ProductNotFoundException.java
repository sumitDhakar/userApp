package com.user.app.exception;

public class ProductNotFoundException extends RuntimeException

{
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
		super("Product Not Found ");
		// TODO Auto-generated constructor stub
	}
	public ProductNotFoundException(String Message) {
		super(Message);
		// TODO Auto-generated constructor stub
	}


	}
