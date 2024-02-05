package com.mogul.demo.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mogul.demo.board.dto.ArticleTagRequest;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.dto.ArticleTagViewResponse;
import com.mogul.demo.board.entity.ArticleTag;
import com.mogul.demo.board.entity.ArticleTagView;
import com.mogul.demo.board.mapper.ArticleTagMapper;
import com.mogul.demo.board.mapper.ArticleTagViewMapper;
import com.mogul.demo.board.repository.ArticleTagRepository;
import com.mogul.demo.board.repository.ArticleTagViewRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleTagServiceImpl implements ArticleTagService{

	private final ArticleTagRepository articleTagRepository;
	private final ArticleTagViewRepository articleTagViewRepository;

	public ArticleTagServiceImpl(ArticleTagRepository articleTagRepository,
		ArticleTagViewRepository articleTagViewRepository) {
		this.articleTagRepository = articleTagRepository;
		this.articleTagViewRepository = articleTagViewRepository;
	}

	@Override
	public ArticleTagResponse addTag(ArticleTagRequest articleTagRequest) {
		// 태그를 추가한다
		ArticleTag tag = ArticleTagMapper.INSTANCE.articleTagRequestToArticleTag(articleTagRequest);
		ArticleTag articleTag = articleTagRepository.save(tag);
		ArticleTagResponse articleTagResponse = ArticleTagMapper.INSTANCE.articleTagToArticleTagResponse(articleTag);
		return articleTagResponse;
	}

	@Override
	public List<ArticleTagResponse> addTagList(List<ArticleTagRequest> articleTagRequestList) {
		List<ArticleTagResponse> articleTagResponseList = new ArrayList<>();
		for(ArticleTagRequest articleTagRequest : articleTagRequestList){
			if(!articleTagRepository.existsByTag(articleTagRequest.getTag())){
				//중복이 아닐경우에만 테이블에 추가합니다
				ArticleTag articleTag = ArticleTagMapper.INSTANCE.articleTagRequestToArticleTag(articleTagRequest);
				ArticleTag savedTag = articleTagRepository.save(articleTag);
				articleTagResponseList.add(ArticleTagMapper.INSTANCE.articleTagToArticleTagResponse(savedTag));
			}else{
				ArticleTagResponse articleTagResponse = getTag(articleTagRequest.getTag());
				articleTagResponseList.add(articleTagResponse);
			}
		}
		return articleTagResponseList;
	}

	@Override
	public ArticleTagResponse getTag(Long id) {
		ArticleTag articleTag = articleTagRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 태그를 찾지 못했습니다"));
		ArticleTagResponse articleTagResponse = ArticleTagMapper.INSTANCE.articleTagToArticleTagResponse(articleTag);
		return articleTagResponse;
	}

	@Override
	public ArticleTagResponse getTag(String tag) {
		ArticleTag articleTag = articleTagRepository.findArticleTagByTag(tag).orElseThrow(()-> new EntityNotFoundException("해당하는 태그를 찾지 못했습니다"));
		ArticleTagResponse articleTagResponse = ArticleTagMapper.INSTANCE.articleTagToArticleTagResponse(articleTag);
		return articleTagResponse;
	}

	@Override
	public Boolean DuplicateTag(String tag) {
		return articleTagRepository.existsByTag(tag);
	}

	@Override
	public List<ArticleTagViewResponse> getArticleTagList(Long articleId) {
		List<ArticleTagViewResponse> articleTagResponseList = new ArrayList<>();
		List<ArticleTagView> articleTagViewList = articleTagViewRepository.findArticleTagViewById(articleId);

		if(articleTagViewList.isEmpty()) return null;

		for(ArticleTagView articleTagView : articleTagViewList){
			ArticleTagViewResponse articleTagViewResponse = ArticleTagViewMapper.INSTANCE.articleTagViewToArticleTagViewResponse(articleTagView);
			articleTagResponseList.add(articleTagViewResponse);
		}

		return articleTagResponseList;
	}
}
