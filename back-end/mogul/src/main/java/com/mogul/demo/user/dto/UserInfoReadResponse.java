package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoReadResponse {
	@NotBlank
	@Email
	@Length(min = 1, max = 320)
	private String email;

	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	private String nickname;

}
