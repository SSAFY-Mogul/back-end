package com.mogul.demo.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleRequest {
	@NotBlank(message = "제목은 비어있을 수 없습니다.")
	@Size(max = 255,message = "제목은 255자를 초과할 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 비어있을 수 없습니다")
	private String content;
}
