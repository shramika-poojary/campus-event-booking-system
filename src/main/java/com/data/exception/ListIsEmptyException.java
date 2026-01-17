package com.data.exception;

import org.springframework.stereotype.Component;

@Component
public class ListIsEmptyException extends RuntimeException {

	String message;

	public ListIsEmptyException(String message) {
		super();
		this.message = message;
	}

	public ListIsEmptyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ListIsEmptyException [message=" + message + "]";
	}

	
	
}
