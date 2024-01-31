package com.mogul.demo.board.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleReadResponse extends ArticleResponse{
	private Long userId;
	private Integer hit;
	private LocalDateTime editedDate;

}
