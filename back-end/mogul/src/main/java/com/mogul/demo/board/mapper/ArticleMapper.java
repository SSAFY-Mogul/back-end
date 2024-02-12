package com.mogul.demo.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.user.entity.User;

@Mapper
public interface ArticleMapper {
	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	// Article <-> ArticleCreateRequest
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "registeredDate", ignore = true)
	@Mapping(target = "editedDate", ignore = true)
	@Mapping(target = "deletedDate", ignore = true)
	@Mapping(target = "hit", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "comments", ignore = true)
	Article articleCreateRequestToArticle(ArticleCreateRequest articleCreateRequest);

	// ArticleCreateRequest articleToArticleCreateRequest(Article article);


	// Article <-> ArticleReadResponse
	// Article articleReadResponseToArticle(ArticleReadResponse articleReadResponse);


	ArticleReadResponse articleToArticleReadResponse(Article article);


	// Article <-> ArticleUpdateRequest
	// Article articleUpdateRequestToArticle(ArticleUpdateRequest articleUpdateRequest);
	//
	// ArticleUpdateRequest articleToArticleUpdateRequest(Article article);
	ArticleResponse articleToArticleResponse(Article article);
}
