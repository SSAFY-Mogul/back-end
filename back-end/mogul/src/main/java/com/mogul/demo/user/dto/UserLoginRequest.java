package com.mogul.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserLoginRequest {
	@Email
	@NotBlank(message = "아이디는 비어있을 수 없습니다.")
	private String username;

	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	@NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
	private String password;
}