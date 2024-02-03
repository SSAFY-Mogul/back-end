package com.mogul.demo.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mogul.demo.board.dto.ArticleTagRequest;
import com.mogul.demo.board.dto.ArticleTagResponse;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.entity.ArticleTag;
import com.mogul.demo.board.mapper.ArticleTagMapper;
import com.mogul.demo.board.repository.ArticleTagRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleTagServiceImpl implements ArticleTagService{

	private final ArticleTagRepository articleTagRepository;

	public ArticleTagServiceImpl(ArticleTagRepository articleTagRepository) {
		this.articleTagRepository = articleTagRepository;
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
}
