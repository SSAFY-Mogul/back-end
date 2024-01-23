package com.mogul.demo.board.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.mapper.ArticleMapper;
import com.mogul.demo.board.repository.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;

	public ArticleServiceImpl(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArticleReadResponse> findArticleList(int page,int size) {
		PageRequest pageable = PageRequest.of(page,size);
		List<ArticleReadResponse> articleList = articleRepository.findAll(pageable)
			.getContent()
			.stream().map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.collect(Collectors.toList());

		return articleList;
	}

	@Override
	@Transactional(readOnly = true)
	public ArticleReadResponse findArticleDetail(int id) {
		ArticleReadResponse article = ArticleMapper.INSTANCE.articleToArticleReadResponse(articleRepository.getArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다.")));
		return article;
	}

	@Override
	//@Transactional
	public ArticleReadResponse addArticle(ArticleCreateRequest articleCreateRequest) {
		Article article = ArticleMapper.INSTANCE.articleCreateRequestToArticle(articleCreateRequest);
		ArticleReadResponse articleReadResponse = ArticleMapper.INSTANCE.articleToArticleReadResponse(articleRepository.save(article)); // 변경된 내용을 리턴한다
		return articleReadResponse;
	}

	@Override
	@Transactional
	public boolean removeArticle(int id) {
		Article article = articleRepository.getArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다."));
		article.deleteArticle();
		Article removeArticle = articleRepository.save(article);
		return Objects.equals(article.getId(), removeArticle.getId());
	}

	@Override
	@Transactional
	public ArticleReadResponse modifyArticle(ArticleUpdateRequest articleUpdateRequest) {
		articleRepository.getArticleById(articleUpdateRequest.getId()).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다"));
		Article modifyArticle = articleRepository.save(ArticleMapper.INSTANCE.articleUpdateRequestToArticle(articleUpdateRequest));
		return ArticleMapper.INSTANCE.articleToArticleReadResponse(modifyArticle);
	}
}
