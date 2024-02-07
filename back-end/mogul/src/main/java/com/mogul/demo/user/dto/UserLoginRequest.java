package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginRequest {
	@NotNull
	@Email
	@Length(min = 1, max = 320)
	private final String email; //email

	@NotNull
	@Pattern(regexp = "[A-Za-z0-9_!@#$]{8,15}$")
	private final String password;
}
