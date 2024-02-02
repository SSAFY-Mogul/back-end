package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCheckEmailRequest {
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9_!@#$]{8,45}$")
	private final String email;
}
