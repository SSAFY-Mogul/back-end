package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	@Schema(example = "test@test.test")
	private final String email;

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9_!@#$]{8,15}$")
	@Setter
	@Schema(example = "testpassword12")
	private String password;


	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	@Schema(example = "testnickname1")
	private final String nickname;
}
