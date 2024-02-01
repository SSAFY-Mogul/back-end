package com.mogul.demo.user.exception;

public class NoSuchUserException extends RuntimeException {

	// public NoSuchUserException() {
	// }

	public NoSuchUserException(String message) {
		super(message);
	}

}