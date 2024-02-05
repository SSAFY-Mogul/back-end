package com.mogul.demo.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleTagRequest {
	@NotBlank(message = "태그는 비어있을 수 없습니다")
	@Size(max = 10,message = "태그의 길이는 10자를 초과할 수 없습니다")
	private String tag;
}
