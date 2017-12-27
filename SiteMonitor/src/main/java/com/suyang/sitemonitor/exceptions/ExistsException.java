package com.suyang.sitemonitor.exceptions;

public class ExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public ExistsException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
