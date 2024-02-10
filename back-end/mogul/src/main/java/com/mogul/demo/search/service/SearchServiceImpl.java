package com.mogul.demo.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mogul.demo.search.dto.WebtoonSearchResponse;
import com.mogul.demo.search.mapper.WebtoonDocumentMapper;
import com.mogul.demo.search.repository.WebtoonSearchRepository;


@Service
public class SearchServiceImpl implements SearchService{
	private final WebtoonSearchRepository webtoonSearchRepository;

	public SearchServiceImpl(WebtoonSearchRepository webtoonSearchRepository) {
		this.webtoonSearchRepository = webtoonSearchRepository;
	}

	@Override
	public List<WebtoonSearchResponse> findByTitle(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findByTitleContaining(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public List<WebtoonSearchResponse> findBySummary(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findBySummaryContaining(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public List<WebtoonSearchResponse> findByGenre(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findByGenreContaining(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public List<WebtoonSearchResponse> search(String keyword) {
		List<WebtoonSearchResponse> list = new ArrayList<>();

		list.addAll(findByTitle(keyword));
		list.addAll(findByGenre(keyword));
		list.addAll(findBySummary(keyword));

		if(list.isEmpty()) throw new NoSuchElementException("검색결과가 없습니다");

		return list;
	}
}
