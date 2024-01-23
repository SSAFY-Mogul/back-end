package com.mogul.demo.board.dto;

import lombok.Data;

@Data
public class ArticleResponse {
	private int id;
	private String title;
	private String content;
}
