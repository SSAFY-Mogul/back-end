package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserJoinRequest {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	private final String email;

	@NotBlank
	@Length(min = 8, max = 45)
	private final String password;

	@NotBlank
	@Length(min = 2, max = 15)
	private final String nickname;
}
