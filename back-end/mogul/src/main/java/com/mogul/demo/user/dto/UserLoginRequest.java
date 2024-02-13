package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLoginRequest {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	@Schema(example = "test@test.test")
	private final String email; //email

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9_!@#$]{8,15}$")
	@Schema(example = "testpassword12")
	private final String password;
}
