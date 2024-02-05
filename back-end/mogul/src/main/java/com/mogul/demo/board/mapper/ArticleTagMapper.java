package com.mogul.demo.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.board.dto.ArticleTagRequest;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.entity.ArticleTag;

@Mapper
public interface ArticleTagMapper {
	ArticleTagMapper INSTANCE = Mappers.getMapper(ArticleTagMapper.class);
	ArticleTagResponse articleTagToArticleTagResponse(ArticleTag articleTag);
	ArticleTag articleTagRequestToArticleTag(ArticleTagRequest articleTagRequest);
	ArticleTagResponse articleTagResponseToArticleTagRequest(ArticleTagRequest articleTagRequest);
}
