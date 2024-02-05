package com.mogul.demo.user.auth.exception;

public class UnauthorizedException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "인가되지 않은 요청입니다.";

	public UnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}

	public UnauthorizedException(String s) {
		super(s);
	}
}
