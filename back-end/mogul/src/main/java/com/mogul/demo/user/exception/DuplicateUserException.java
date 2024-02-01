package com.mogul.demo.user.exception;

public class DuplicateUserException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "Duplicated Email";

	public DuplicateUserException() {
	}

	public DuplicateUserException(String message) {
		super(message);
	}

	public DuplicateUserException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public DuplicateUserException(String message, Throwable cause) {
		super(message, cause);
	}
}