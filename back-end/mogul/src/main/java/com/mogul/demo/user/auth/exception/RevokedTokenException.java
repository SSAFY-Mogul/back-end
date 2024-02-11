package com.mogul.demo.user.auth.exception;

public class RevokedTokenException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "로그아웃된 사용자입니다.";
	
	public RevokedTokenException() {
		this(DEFAULT_MESSAGE);
	}

	public RevokedTokenException(String message) {
		super(message);
	}
}
