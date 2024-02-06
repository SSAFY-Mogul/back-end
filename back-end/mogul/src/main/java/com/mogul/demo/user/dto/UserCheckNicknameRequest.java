package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCheckNicknameRequest {
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	private String nickname;
}
