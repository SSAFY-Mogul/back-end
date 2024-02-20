package com.mogul.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
	@NotBlank(message = "아이디는 비어있을 수 없습니다.")
	private String username;
	@NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
	private String password;
}