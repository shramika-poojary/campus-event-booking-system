package com.data.exception;

import org.springframework.stereotype.Component;

@Component
public class EventAlreadyBookedException extends RuntimeException {

	String message;

	public EventAlreadyBookedException(String message) {
		super();
		this.message = message;
	}

	public EventAlreadyBookedException() {
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
		return "EventAlreadyBookedException [message=" + message + "]";
	}

		
	
}
