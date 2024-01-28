package com.mogul.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class UserJoinDto {
	@Email
	@NotNull
	private String email;

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,32}$")
	@NotNull
	private String password;

	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	@NotNull
	private String nickname;
}
