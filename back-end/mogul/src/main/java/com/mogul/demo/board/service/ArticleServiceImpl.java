package com.mogul.demo.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.ArticleResponse;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.dto.ArticleTagViewResponse;
import com.mogul.demo.board.dto.ArticleTotalSearchResponse;
import com.mogul.demo.board.dto.ArticleUpdateRequest;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.entity.ArticleTagView;
import com.mogul.demo.board.mapper.ArticleMapper;
import com.mogul.demo.board.repository.ArticleRepository;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleServiceImpl implements ArticleService{

	private final ArticleRepository articleRepository;
	private final UserService userService;
	private final ArticleTagService articleTagService;
	private final ArticleArticleTagService articleArticleTagService;

	public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService,
		ArticleTagService articleTagService, ArticleArticleTagService articleArticleTagService) {
		this.articleRepository = articleRepository;
		this.userService = userService;
		this.articleTagService = articleTagService;
		this.articleArticleTagService = articleArticleTagService;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArticleReadResponse> findArticleList(int page,int size) {
		PageRequest pageable = PageRequest.of(page,size, Sort.by("id").descending());

		List<ArticleReadResponse> articleList = articleRepository.findAllByIsDeletedFalse(pageable)
			.stream().map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.collect(Collectors.toList());

		if(articleList.isEmpty()) throw new EntityNotFoundException("작성된 게시글이 없습니다");

		return articleList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArticleReadResponse> findArticleListByUser(int page, int size) {
		PageRequest pageable = PageRequest.of(page,size,Sort.by("id").descending());

		List<ArticleReadResponse> articleList = articleRepository.findAllByIsDeletedFalseAndUser(pageable,userService.getUserFromAuth())
			.stream().map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.collect(Collectors.toList());

		if(articleList.isEmpty()) throw new EntityNotFoundException("작성한 게시글이 없습니다");

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
		List<ArticleTagViewResponse> tags = articleTagService.getArticleTagList(id);
		article.setArticleTagList(tags);
		return article;
	}

	@Override
	@Transactional
	public ArticleReadResponse addArticle(ArticleCreateRequest articleCreateRequest) {
		User user = userService.getUserFromAuth();
		// 게시글 작성하는 유저
		Article article = ArticleMapper.INSTANCE.articleCreateRequestToArticle(articleCreateRequest);

		article.setUser(user);

		Article savedArticle = articleRepository.save(article);
		// 게시글 생성

		List<ArticleTagResponse> articleTagResponseList = articleTagService.addTagList(articleCreateRequest.getArticleTagList());
		// 해시태그 데이터 생성

		articleArticleTagService.addRelationList(savedArticle.getId(), articleTagResponseList);
		// 해시태그 중계 테이블 데이터 생성

		ArticleReadResponse articleReadResponse = findArticleDetail(article.getId());
		// 데이터 다시 가져오기

		return articleReadResponse;
	}

	@Override
	@Transactional
	public boolean removeArticle(Long id) {
		Article article = articleRepository.findArticleById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다."));
		User user = userService.getUserFromAuth();

		if(!article.getUser().equals(user)) throw new RuntimeException("유효하지 않은 요청입니다");

		article.deleteArticle();
		Article removeArticle = articleRepository.save(article);
		return Objects.equals(article.getId(), removeArticle.getId());
	}

	@Override
	@Transactional
	public ArticleReadResponse modifyArticle(ArticleUpdateRequest articleUpdateRequest) {
		Article article = articleRepository.findArticleById(articleUpdateRequest.getId()).orElseThrow(()-> new EntityNotFoundException("해당하는 게시글이 없습니다"));

		User user = userService.getUserFromAuth();

		if(!article.getUser().equals(user)) throw new IllegalArgumentException("유효하지 않은 요청입니다");

		article.updateArticle(articleUpdateRequest.getTitle(),articleUpdateRequest.getContent());

		ArticleReadResponse articleReadResponse = findArticleDetail(articleRepository.save(article).getId());

		return articleReadResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public Article findByArticleId(Long id) {
		Article article = articleRepository.findArticleById(id).orElseThrow(()->new RuntimeException("해당하는 게시글이 없습니다"));
		return article;
	}

	@Override
	@Transactional(readOnly = true)
	public int findByArticleCount() {
		return articleRepository.countArticleByIsDeletedFalse();
	}

	@Override
	public List<ArticleReadResponse> findByArticleTitle(String title) {
		List<ArticleReadResponse> list = articleRepository.findByTitleAndIsDeletedFalse(title)
			.stream().map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.map(articleReadResponse -> {
				articleReadResponse.setArticleTagList(articleTagService.getArticleTagList(articleReadResponse.getId()));
				return articleReadResponse;
			})
			.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<ArticleReadResponse> findByArticleContent(String content) {
		List<ArticleReadResponse> list = articleRepository.findByContentAndIsDeletedFalse(content)
			.stream()
			.map(ArticleMapper.INSTANCE::articleToArticleReadResponse)
			.map(articleReadResponse -> {
				articleReadResponse.setArticleTagList(articleTagService.getArticleTagList(articleReadResponse.getId()));
				return articleReadResponse;
			})
			.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<ArticleReadResponse> findByArticleTag(String tag) {
		List<ArticleTagView> tagViewList = articleTagService.findArticleByTag(tag);
		List<ArticleReadResponse> list = new ArrayList<>();

		for(ArticleTagView tagView : tagViewList){
			ArticleReadResponse  articleResponse = findArticleDetail(tagView.getId());
			list.add(articleResponse);
		}

		return list;
	}

	@Override
	public ArticleTotalSearchResponse articleTotalSearch(String keyword) {
		ArticleTotalSearchResponse articleTotalSearchResponse = new ArticleTotalSearchResponse();

		articleTotalSearchResponse.setTitle(findByArticleTitle(keyword));
		articleTotalSearchResponse.setContent(findByArticleContent(keyword));
		articleTotalSearchResponse.setTag(findByArticleTag(keyword));

		return articleTotalSearchResponse;
	}

}
