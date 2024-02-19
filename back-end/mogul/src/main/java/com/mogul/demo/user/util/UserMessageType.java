package com.mogul.demo.user.util;

public enum UserMessageType {
	REGISTRATION_SUCCESS("회원가입이 성공적으로 이루어졌습니다."),
	LOGIN_SUCCESS("로그인이 성공적으로 이루어졌습니다")
	// 다른 메시지들도 필요에 따라 추가할 수 있습니다.
	;
	private final String message;

	UserMessageType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}