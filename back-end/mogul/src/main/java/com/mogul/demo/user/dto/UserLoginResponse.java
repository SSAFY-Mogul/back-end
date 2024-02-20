package com.mogul.demo.user.dto;

import lombok.Data;

@Data
public class UserLoginResponse {
	private String token;
	private String nickname;
}