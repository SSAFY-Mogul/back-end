package com.mogul.demo.board.dto;

import java.util.List;

import lombok.Data;

@Data
public class ArticleTotalSearchResponse {
	private List<ArticleReadResponse> title;
	private List<ArticleReadResponse> content;
	private List<ArticleReadResponse> tag;
}
