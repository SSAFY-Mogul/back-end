package com.mogul.demo.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
import com.mogul.demo.board.service.CommentService;
import com.mogul.demo.util.CustomResponse;

@RestController
@RequestMapping("/board/{articleId}/comment")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping()
	public ResponseEntity<CustomResponse> CommentList(@PathVariable("articleId")int articleId){
		List<CommentGroupResponse> commentGroupResponses = commentService.findCommentList(articleId);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(), commentGroupResponses,"댓글을 조회하였습니다"));
	}

	@PostMapping()
	public ResponseEntity<CustomResponse> CommentAdd(@PathVariable("articleId")int articleId,@RequestBody CommentCreateRequest commentCreateRequest){
		if(articleId != commentCreateRequest.getArticle()) return ResponseEntity.ok(new CustomResponse(HttpStatus.NOT_ACCEPTABLE.value(),"","잘못된 요청값입니다."));
		CommentReadResponse commentReadResponse = commentService.addComment(commentCreateRequest);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.CREATED.value(),commentReadResponse,"댓글이 생성되었습니다."));
	}

	@DeleteMapping("{commentId}")
	public ResponseEntity<CustomResponse> CommentDelete(@PathVariable("commentId")int commentId){
		boolean success = commentService.removeComment(commentId);
		if(!success) return ResponseEntity.ok(new CustomResponse(HttpStatus.NOT_ACCEPTABLE.value(), "","댓글이 삭제되지않았습니다"));
		return ResponseEntity.ok(new CustomResponse(HttpStatus.ACCEPTED.value(),"","댓글이 성공적으로 삭제되었습니다."));
	}



}
