package com.mogul.demo.board.service;

import java.util.List;

import com.mogul.demo.board.dto.ArticleTagRequest;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.dto.ArticleTagViewResponse;
import com.mogul.demo.board.entity.ArticleTagView;
import com.mogul.demo.board.repository.ArticleTagRepository;


public interface ArticleTagService {
	ArticleTagResponse addTag(ArticleTagRequest articleTagRequest);
	List<ArticleTagResponse> addTagList(List<ArticleTagRequest> articleTagRequestList);
	ArticleTagResponse getTag(Long id);
	ArticleTagResponse getTag(String tag);
	Boolean DuplicateTag(String tag);
	List<ArticleTagViewResponse> getArticleTagList(Long articleId);
	List<ArticleTagView> findArticleByTag(String tag);
}
