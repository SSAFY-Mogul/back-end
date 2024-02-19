package com.mogul.demo.board.dto;


import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCreateRequest extends ArticleRequest{
	private List<ArticleTagRequest> articleTagList; // 해시태그
}
