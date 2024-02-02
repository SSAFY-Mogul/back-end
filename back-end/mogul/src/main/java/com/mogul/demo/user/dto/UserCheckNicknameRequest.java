package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCheckNicknameRequest {
	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	private final String nickname;
}
