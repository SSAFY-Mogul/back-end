package com.mogul.demo.board.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

		List<ArticleReadResponse> articleList = articleRepository.findAllByIsDeletedFalse(pageable)
			.stream().map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.collect(Collectors.toList());

		if(articleList.isEmpty()) throw new EntityNotFoundException("작성된 게시글이 없습니다");

		return articleList;
	}

	@Override
	@Transactional
	public void updateArticleHit(Long id) {
		Article article = articleRepository.findArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다."));
		article.updateHit();
		articleRepository.save(article);
	}

	@Override
	@Transactional(readOnly = true)
	public ArticleReadResponse findArticleDetail(Long id) {
		ArticleReadResponse article = ArticleMapper.INSTANCE.articleToArticleReadResponse(articleRepository.findArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다.")));
		return article;
	}

	@Override
	@Transactional
	public ArticleReadResponse addArticle(ArticleCreateRequest articleCreateRequest) {
		Article article = ArticleMapper.INSTANCE.articleCreateRequestToArticle(articleCreateRequest);
		ArticleReadResponse articleReadResponse = ArticleMapper.INSTANCE.articleToArticleReadResponse(articleRepository.save(article)); // 변경된 내용을 리턴한다
		return articleReadResponse;
	}

	@Override
	@Transactional
	public boolean removeArticle(Long id) {
		Article article = articleRepository.findArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다."));
		article.deleteArticle();
		Article removeArticle = articleRepository.save(article);
		return Objects.equals(article.getId(), removeArticle.getId());
	}

	@Override
	@Transactional
	public ArticleReadResponse modifyArticle(ArticleUpdateRequest articleUpdateRequest) {
		Article article = articleRepository.findArticleById(articleUpdateRequest.getId()).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다"));
		article.updateArticle(articleUpdateRequest.getTitle(),articleUpdateRequest.getContent());
		Article modifyArticle = articleRepository.save(article);
		return ArticleMapper.INSTANCE.articleToArticleReadResponse(modifyArticle);
	}
}
