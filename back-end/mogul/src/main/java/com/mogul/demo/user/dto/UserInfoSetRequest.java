package com.mogul.demo.user.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoSetRequest {
	@NotBlank
	@Pattern(regexp = "[가-힣A-Za-z0-9]{2,15}")
	private String nickname;

	// @NotBlank
	// @Pattern(regexp = "[A-Za-z0-9_!@#$]{8,15}$")
	// private String password;
}
