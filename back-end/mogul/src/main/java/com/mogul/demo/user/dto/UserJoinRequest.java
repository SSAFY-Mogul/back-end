package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class UserJoinRequest {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	private final String email;

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9_!@#$]{8,45}$")
	private final String password;


	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	private final String nickname;
}
