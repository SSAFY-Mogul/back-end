package com.mogul.demo.search.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mogul.demo.search.dto.WebtoonSearchResponse;
import com.mogul.demo.search.dto.WebtoonTotalSearchResponse;
import com.mogul.demo.search.mapper.WebtoonDocumentMapper;
import com.mogul.demo.search.repository.WebtoonSearchRepository;


@Service
public class WebtoonSearchServiceImpl implements WebtoonSearchService {
	private final WebtoonSearchRepository webtoonSearchRepository;

	public WebtoonSearchServiceImpl(WebtoonSearchRepository webtoonSearchRepository) {
		this.webtoonSearchRepository = webtoonSearchRepository;
	}

	@Override
	public List<WebtoonSearchResponse> findByTitle(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findByTitle(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public List<WebtoonSearchResponse> findBySummary(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findBySummary(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public List<WebtoonSearchResponse> findByGenre(String keyword) {
		List<WebtoonSearchResponse> webtoonSearchResponseList = webtoonSearchRepository
			.findByGenre(keyword)
			.stream()
			.map(WebtoonDocumentMapper.INSTANCE::webtoonDocuementToWebtoonSearchResponse)
			.collect(Collectors.toList());

		return webtoonSearchResponseList;
	}

	@Override
	public WebtoonTotalSearchResponse totalSearch(String keyword) {
		WebtoonTotalSearchResponse webtoonTotalSearchResponse = new WebtoonTotalSearchResponse();

		webtoonTotalSearchResponse.setTitle(findByTitle(keyword));
		webtoonTotalSearchResponse.setGenre(findByGenre(keyword));
		webtoonTotalSearchResponse.setSummary(findBySummary(keyword));

		return webtoonTotalSearchResponse;
	}


}
