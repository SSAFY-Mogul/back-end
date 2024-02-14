package com.mogul.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCheckNicknameRequest {
	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	@Schema(example = "testnickname1")
	private String nickname;
}
