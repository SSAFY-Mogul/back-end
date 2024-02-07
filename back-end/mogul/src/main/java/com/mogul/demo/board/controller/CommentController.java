package com.mogul.demo.board.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
import com.mogul.demo.board.service.CommentService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.util.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/board")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/{articleId}/comment")
	@Operation(summary = "댓글 조회", description = "게시글 id를 이용하여 해당 게시글의 댓글을 모두 조회합니다", responses = {
		@ApiResponse(responseCode = "200", description = "댓글 조회 성공", content = @Content(schema = @Schema(implementation = CommentReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> CommentList(@PathVariable("articleId")Long articleId){
		List<CommentGroupResponse> commentGroupResponses = commentService.findCommentList(articleId);
		return new ResponseEntity<>(new CustomResponse(HttpStatus.OK.value(), commentGroupResponses,"댓글 조회 성공"),HttpStatus.ACCEPTED);
	}


	@PostMapping("/{articleId}/comment")
	@Operation(summary = "댓글 작성", description = "댓글을 작성합니다", responses = {
		@ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CommentReadResponse.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> CommentAdd(@PathVariable("articleId")Long articleId,@Valid @RequestBody CommentCreateRequest commentCreateRequest,
		BindingResult bindingResult){

		if(articleId != commentCreateRequest.getArticle().getId()) return ResponseEntity.ok(new CustomResponse(HttpStatus.NOT_ACCEPTABLE.value(),"","요청하신 게시글의 아이디가 다릅니다."));

		if(bindingResult.hasErrors()) {
			String errorMessages = bindingResult.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining("\n"));
			return ResponseEntity.ok(new CustomResponse(HttpStatus.BAD_REQUEST.value(),errorMessages,"잘못된 요청입니다."));
		}
		CommentReadResponse commentReadResponse = commentService.addComment(commentCreateRequest);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.CREATED.value(),commentReadResponse,"댓글이 생성되었습니다."));
	}

	@DeleteMapping("/{articleId}/comment/{commentId}")
	@Operation(summary = "댓글 삭제", description = "댓글 ID를 이용하여 해당 댓글을 삭제합니다", responses = {
		@ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = @Content(schema = @Schema(implementation = CommentReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> CommentDelete(@PathVariable("commentId")Long commentId){
		boolean success = commentService.removeComment(commentId);

		if(!success) new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND.value(),"","댓글이 삭제되지 않았습니다"),HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(new CustomResponse(HttpStatus.ACCEPTED.value(),"","댓글이 성공적으로 삭제되었습니다."),HttpStatus.OK);
	}

	

}
