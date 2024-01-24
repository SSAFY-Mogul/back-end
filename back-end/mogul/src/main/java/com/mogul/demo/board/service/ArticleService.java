package com.mogul.demo.board.service;

import java.util.List;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;

public interface ArticleService {
	/*
	 * todo
	 * 게시글 목록 조회
	 * 게시글 상세 조회
	 * 게시글 작성
	 * 게시글 수정
	 * 게시글 삭제
	 * 게시글 좋아요
	 * 게시글 좋아요 취소
	 * */

	List<ArticleReadResponse> findArticleList(int page,int size);

	ArticleReadResponse findArticleDetail(int id);

	ArticleReadResponse addArticle(ArticleCreateRequest articleCreateRequest);

	boolean removeArticle(int id);

	ArticleReadResponse modifyArticle(ArticleUpdateRequest articleUpdateRequest);

}

