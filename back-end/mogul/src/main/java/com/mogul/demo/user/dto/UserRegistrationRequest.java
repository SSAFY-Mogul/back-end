package com.mogul.demo.user.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {
	private String username;
	private String password;
	private String nickname;
}