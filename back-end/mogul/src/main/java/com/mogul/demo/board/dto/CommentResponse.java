package com.mogul.demo.board.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponse {
	private int id;
	private String content;
	private LocalDateTime registeredDate;
}
