package com.mogul.demo.board.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentCreateRequest extends CommentRequest{
	private int group;
	// 댓글이 어디 그룹인지
	// id와 group이 같으면 부모댓글
	// 아니면 자식 댓글
	private ArticleResponse article; // 어떤 게시글의 댓글인지
}
