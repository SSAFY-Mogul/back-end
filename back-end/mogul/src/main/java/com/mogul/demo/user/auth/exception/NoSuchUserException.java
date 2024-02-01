package com.mogul.demo.user.auth.exception;

public class NoSuchUserException extends RuntimeException {
	public NoSuchUserException() {
	}

	public NoSuchUserException(String message) {
		super(message);
	}
}
