package com.servicecomb.base.exception;

public class CustomException extends RuntimeException{
	private static final long serialVersionUID = -4798903918077971L;
	
	public CustomException(String message) {
		super(message);
	}
}
