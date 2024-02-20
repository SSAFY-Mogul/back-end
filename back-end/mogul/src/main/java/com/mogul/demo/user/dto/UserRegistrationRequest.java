package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequest {
	@NotBlank(message = "아이디는 비어있을 수 없습니다")
	private String username;
	@NotBlank(message = "비밀번호는 비어있을 수 없습니다")
	private String password;
	@NotBlank(message = "닉네임은 비어있을 수 없습니다")
	private String nickname;
}