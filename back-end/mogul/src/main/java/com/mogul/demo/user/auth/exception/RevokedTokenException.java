package com.mogul.demo.user.auth.exception;

public class RevokedTokenException extends RuntimeException {
	private static String DEFAULT_MESSAGE = "파기된 인증 정보입니다. 다시 로그인해주세요.";
	
	public RevokedTokenException() {
		this(DEFAULT_MESSAGE);
	}

	public RevokedTokenException(String message) {
		super(message);
	}
}
