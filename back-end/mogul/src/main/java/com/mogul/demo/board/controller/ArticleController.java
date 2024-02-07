package com.mogul.demo.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.service.ArticleService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.util.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/board")
@Tag(name = "Article", description = "게시글 API")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping()
	@Operation(summary = "게시글 조회", description = "게시글 리스트를 조회합니다", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 리스트 조회 성공", content = @Content(schema = @Schema(implementation = ArticleReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> ArticleList(@RequestParam("pno")int page,@RequestParam("count")int size){
		List<ArticleReadResponse> articleList = articleService.findArticleList(page,size);
		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.ACCEPTED.value(),articleList,"게시글 조회 성공"));
	}

	@GetMapping("{id}")
	@Operation(summary = "게시글 조회", description = "게시글 id를 이용하여 해당 게시글을 조회합니다", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = ArticleReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> ArticleDetail(@PathVariable("id")Long id){
		articleService.updateArticleHit(id);
		ArticleReadResponse article = articleService.findArticleDetail(id);
		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.ACCEPTED.value(),article,"게시글 상세 조회 성공"));
	}

	@PostMapping()
	@Operation(summary = "게시글 생성", description = "게시글을 생성합니다", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 생성 성공", content = @Content(schema = @Schema(implementation = ArticleReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> ArticleAdd(@RequestBody @Valid ArticleCreateRequest article, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			// 유효성 검사에 실패한 경우 에러 처리 로직 수행
			return ResponseEntity.ok(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(),"","잘못된 요청입니다."));
		}
		ArticleReadResponse articleReadResponse = articleService.addArticle(article);

		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.CREATED.value(),articleReadResponse,"게시글이 성공적으로 생성되었습니다"));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<CustomResponse> ArticleDelete(@PathVariable("id")Long id){
		boolean isDeleted = articleService.removeArticle(id);
		if(!isDeleted) return ResponseEntity.ok(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(),"","게시글이 삭제되지 않았습니다"));
		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(),"","게시글이 성공적으로 삭제되었습니다"));
	}

	@PatchMapping()
	@Operation(summary = "게시글 수정", description = "게시글 수정 성공", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = ArticleReadResponse.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<CustomResponse> ArticleUpdate(@RequestBody @Valid ArticleUpdateRequest articleUpdateRequest){
		ArticleReadResponse articleReadResponse = articleService.modifyArticle(articleUpdateRequest);
		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.ACCEPTED.value(),articleReadResponse,"게시글이 성공적으로 수정되었습니다"));
	}

	@GetMapping("/count")
	@Operation(summary = "게시글 전체 개수 조회", description = "게시글 전체 개수 조회 성공", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 전체 개수 조회 성공"),
	})
	public ResponseEntity<CustomResponse> ArticleCount(){
		int count = articleService.findByArticleCount();
		return ResponseEntity.ok(new CustomResponse(HttpStatus.ACCEPTED.value(),count,"게시글 전체 개수 조회 성공"));
	}


	@GetMapping("/my-article")
	@Operation(summary = "내가 작성한 게시글 리스트 조회" , description = "게시글 전체 개수 조회 성공", responses = {
		@ApiResponse(responseCode = "200", description = "게시글 전체 개수 조회 성공"),
	} )
	public ResponseEntity<CustomResponse> MyArticleList(@RequestParam("pno")int page,@RequestParam("count")int size){
		List<ArticleReadResponse> list = articleService.findArticleListByUser(page,size);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(),list,"내가 작성한 게시글 리스트 조회 성공"));
	}

}
