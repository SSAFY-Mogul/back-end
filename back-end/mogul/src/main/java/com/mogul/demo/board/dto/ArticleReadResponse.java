package com.mogul.demo.board.dto;

import java.time.LocalDateTime;

import com.mogul.demo.user.dto.UserResponse;

import lombok.Data;

@Data
public class ArticleReadResponse extends ArticleResponse{
	private Integer hit;
	private LocalDateTime editedDate;
	private UserResponse user;
}
