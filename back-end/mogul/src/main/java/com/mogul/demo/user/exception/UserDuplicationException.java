package com.mogul.demo.user.exception;

public class UserDuplicationException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "Duplicated Email";

	public UserDuplicationException() {
	}

	public UserDuplicationException(String message) {
		super(message);
	}

	public UserDuplicationException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public UserDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}