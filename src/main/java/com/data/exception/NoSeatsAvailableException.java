package com.data.exception;

import org.springframework.stereotype.Component;

@Component
public class NoSeatsAvailableException extends RuntimeException {

	String message;

	public NoSeatsAvailableException(String message) {
		super();
		this.message = message;
	}

	public NoSeatsAvailableException() {
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
		return "NoSeatsAvailableException [message=" + message + "]";
	}

	
	
}
