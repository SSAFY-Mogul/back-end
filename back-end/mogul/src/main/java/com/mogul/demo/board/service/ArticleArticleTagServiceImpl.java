package com.mogul.demo.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.board.dto.ArticleTagRequest;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.entity.ArticleArticleTag;
import com.mogul.demo.board.entity.ArticleArticleTagPK;
import com.mogul.demo.board.repository.ArticleArticleTagRepository;

@Service
public class ArticleArticleTagServiceImpl implements ArticleArticleTagService{
	private final ArticleArticleTagRepository articleArticleTagRepository;

	public ArticleArticleTagServiceImpl(ArticleArticleTagRepository articleArticleTagRepository) {
		this.articleArticleTagRepository = articleArticleTagRepository;
	}


	@Override
	@Transactional
	public void addRelation(Long articleId, Long articleTagId) {
		ArticleArticleTagPK articleArticleTagPK = new ArticleArticleTagPK(articleId,articleTagId);
		ArticleArticleTag articleTag = new ArticleArticleTag();
		articleTag.setId(articleArticleTagPK);

		articleArticleTagRepository.save(articleTag);
	}


	@Override
	@Transactional
	public void addRelationList(Long articleId, List<ArticleTagResponse> articleTagList) {
		for(ArticleTagResponse articleTagResponse : articleTagList){
			addRelation(articleId,articleTagResponse.getId());
		}
	}

}
