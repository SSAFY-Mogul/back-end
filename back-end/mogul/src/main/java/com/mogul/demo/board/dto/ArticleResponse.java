package com.mogul.demo.board.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticleResponse {
	@Min(value = 1,message = "게시글 아이디는 1부터 시작합니다")
	@NotNull(message = "게시글 ID는 비어있을 수 없습니다")
	private Long id;
}
