package com.mogul.demo.board.dto;

import lombok.Data;

@Data
public class CommentRequest {
	private String content; // 댓글 내용
	private int user; // 댓글 작성자
	private int article; // 어떤 게시글의 댓글인지
}
