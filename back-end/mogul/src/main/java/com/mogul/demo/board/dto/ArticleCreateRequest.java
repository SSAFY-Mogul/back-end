package com.mogul.demo.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleCreateRequest extends ArticleRequest{
	private Long userId;
}
