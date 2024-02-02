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
import com.mogul.demo.user.dto.UserRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.mapper.UserMapper;
import com.mogul.demo.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;
	private final UserService userService;

	public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
		this.articleRepository = articleRepository;
		this.userService = userService;
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
		User user = userService.findUserById(articleCreateRequest.getUser().getId());
		Article article = ArticleMapper.INSTANCE.articleCreateRequestToArticle(articleCreateRequest);
		article.setUser(user);
		Article savedArticle = articleRepository.save(article);
		ArticleReadResponse articleReadResponse = ArticleMapper.INSTANCE.articleToArticleReadResponse(savedArticle); // 변경된 내용을 리턴한다
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

	@Override
	public Article findByArticleId(Long id) {
		Article article = articleRepository.findArticleById(id).orElseThrow(()->new RuntimeException("해당하는 게시글이 없습니다"));
		return article;
	}
}
