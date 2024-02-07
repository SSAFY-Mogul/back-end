package com.mogul.demo.board.service;

import java.util.List;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.entity.Article;

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
	List<ArticleReadResponse> findArticleListByUser(int page,int size);

	void updateArticleHit(Long id);

	ArticleReadResponse addArticle(ArticleCreateRequest articleCreateRequest);

	ArticleReadResponse findArticleDetail(Long id);
	boolean removeArticle(Long id);
	ArticleReadResponse modifyArticle(ArticleUpdateRequest articleUpdateRequest);
	Article findByArticleId(Long id);
	int findByArticleCount();


}

