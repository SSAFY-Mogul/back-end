package com.mogul.demo.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.dto.ArticleTagViewResponse;
import com.mogul.demo.board.entity.ArticleTagView;

@Mapper
public interface ArticleTagViewMapper {
	ArticleTagViewMapper INSTANCE = Mappers.getMapper(ArticleTagViewMapper.class);
	ArticleTagViewResponse articleTagViewToArticleTagViewResponse(ArticleTagView articleTagView);
}
