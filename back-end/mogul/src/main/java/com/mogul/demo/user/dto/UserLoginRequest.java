package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginRequest {
	@NotNull
	private final String email; //email
	@NotNull
	private final String password;
}
