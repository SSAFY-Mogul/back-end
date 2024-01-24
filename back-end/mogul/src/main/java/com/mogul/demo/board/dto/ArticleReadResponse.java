package com.mogul.demo.board.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleReadResponse extends ArticleResponse{
	private int userId;
	private int hit;
	private LocalDateTime editedDate;

}
