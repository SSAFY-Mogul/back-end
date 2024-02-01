package com.mogul.demo.board.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Comment Response")
public class CommentResponse {
	@Schema(description = "댓글 ID")
	private int id;
	@Schema(description = "댓글 내용")
	private String content;
	@Schema(description = "댓글 작성일")
	private LocalDateTime registeredDate;
}
