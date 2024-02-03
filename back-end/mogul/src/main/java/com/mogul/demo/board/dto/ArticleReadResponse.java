package com.mogul.demo.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mogul.demo.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Schema(description = "Article Read Response")
public class ArticleReadResponse extends ArticleResponse{
	private String title;
	private String content;
	private Integer hit;
	private LocalDateTime editedDate;
	private UserResponse user;
	private List<ArticleTagResponse> articleTagList;
}
