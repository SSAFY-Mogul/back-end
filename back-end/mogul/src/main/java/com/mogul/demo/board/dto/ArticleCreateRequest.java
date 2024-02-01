package com.mogul.demo.board.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCreateRequest extends ArticleRequest{
	private Long userId;
}
