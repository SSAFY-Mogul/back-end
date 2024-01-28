package com.mogul.demo.user.dto;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginResponse {
	private final int statusCode;
	private final String statusMessage;
	private final String message;

	public String toJson() {
		return new Gson().toJson(this);
	}
}
