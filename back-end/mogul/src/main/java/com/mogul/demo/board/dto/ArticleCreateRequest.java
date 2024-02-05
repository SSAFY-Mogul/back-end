package com.mogul.demo.board.dto;


import java.util.List;

import com.mogul.demo.user.dto.UserRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleCreateRequest extends ArticleRequest{
	private UserRequest user; // 게시글 작성자
	private List<ArticleTagRequest> articleTagList; // 해시태그
}
