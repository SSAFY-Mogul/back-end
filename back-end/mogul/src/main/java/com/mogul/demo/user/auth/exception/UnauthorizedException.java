package com.mogul.demo.user.auth.exception;

public class UnauthorizedException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "인증 정보가 없습니다";

	public UnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}

	public UnauthorizedException(String s) {
		super(s);
	}
}
