package com.mogul.demo.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.service.ArticleService;
import com.mogul.demo.board.util.CustomResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("board")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}


	@GetMapping()
	public ResponseEntity<List<ArticleReadResponse>> ArticleList(@RequestParam("pno")int page,@RequestParam("count")int size){
		List<ArticleReadResponse> articleList = articleService.findArticleList(page,size);
		return new ResponseEntity<>(articleList,HttpStatus.ACCEPTED);
	}

	@GetMapping("{id}")
	public ResponseEntity<ArticleReadResponse> ArticleDetail(@PathVariable("id")int id){
		ArticleReadResponse article = articleService.findArticleDetail(id);
		return new ResponseEntity<>(article,HttpStatus.ACCEPTED);
	}

	@PostMapping()
	public ResponseEntity<CustomResponse> ArticleAdd(@RequestBody @Valid ArticleCreateRequest article, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			// 유효성 검사에 실패한 경우 에러 처리 로직 수행
			return ResponseEntity.ok(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(),"","잘못된 요청입니다."));
		}
		ArticleReadResponse articleReadResponse = articleService.addArticle(article);
		return ResponseEntity.ok(new CustomResponse<>(HttpStatus.CREATED.value(),articleReadResponse,"게시글이 성공적으로 생성되었습니다"));
	}
}
