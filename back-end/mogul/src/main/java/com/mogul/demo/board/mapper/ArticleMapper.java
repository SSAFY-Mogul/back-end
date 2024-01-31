package com.mogul.demo.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.dto.ArticleCreateRequest;

@Mapper
public interface ArticleMapper {
	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	// Article <-> ArticleCreateRequest
	Article articleCreateRequestToArticle(ArticleCreateRequest articleCreateRequest);

	ArticleCreateRequest articleToArticleCreateRequest(Article article);


	// Article <-> ArticleReadResponse
	Article articleReadResponseToArticle(ArticleReadResponse articleReadResponse);

	ArticleReadResponse articleToArticleReadResponse(Article article);


	// Article <-> ArticleUpdateRequest
	Article articleUpdateRequestToArticle(ArticleUpdateRequest articleUpdateRequest);

	ArticleUpdateRequest articleToArticleUpdateRequest(Article article);


}
