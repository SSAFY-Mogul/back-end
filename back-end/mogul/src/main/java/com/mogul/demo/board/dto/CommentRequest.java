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

	@Min(value = 1,message = "유저 아이디는 1부터 시작합니다")
	@NotNull(message = "댓글 작성자는 비어있을 수 없습니다")
	private int userId; // 댓글 작성자

	@Min(value = 1,message = "게시글 아이디는 1부터 시작합니다")
	@NotNull(message = "게시글 ID는 비어있을 수 없습니다")
	private int articleId; // 어떤 게시글의 댓글인지
}
