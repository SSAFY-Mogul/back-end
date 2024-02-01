package com.mogul.demo.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	@Min(value = 1,message = "유저 아이디는 1부터 시작합니다")
	@NotNull(message = "댓글 작성자는 비어있을 수 없습니다")
	Long id;
}