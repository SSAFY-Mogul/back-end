package com.mogul.demo.board.service;

import java.util.List;

import com.mogul.demo.board.dto.ArticleTagResponse;

public interface ArticleArticleTagService {
	void addRelation(Long articleId,Long articleTagId);

	void addRelationList(Long articleId, List<ArticleTagResponse> articleTagList);
}
