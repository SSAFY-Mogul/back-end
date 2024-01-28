package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserJoinRequest {
	@NotNull
	private final String email;
	@NotNull
	private final String password;
	@NotNull
	private final String nickname;
}
