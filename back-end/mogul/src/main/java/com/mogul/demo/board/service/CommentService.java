package com.mogul.demo.board.service;

import java.util.List;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;

public interface CommentService {
	/*
	* todo
	* 댓글 그룹 조회
	* 댓글 작성
	* 댓글 삭제
	* */
	List<CommentGroupResponse> findCommentList(int articleId);
	CommentReadResponse addComment(CommentCreateRequest commentCreateRequest);
	boolean removeComment(int id);
}
