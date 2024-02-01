package com.mogul.demo.board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleUpdateRequest extends ArticleRequest{
	private Long id;
}
