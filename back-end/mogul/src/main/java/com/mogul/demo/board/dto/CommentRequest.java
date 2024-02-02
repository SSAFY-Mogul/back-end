package com.mogul.demo.board.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

	@NotBlank(message = "내용은 비어있을 수 없습니다")
	@Size(max=150,message = "최대 150자까지 작성 가능합니다")
	private String content; // 댓글 내용

}
