package com.mogul.demo.search.service;

import java.util.List;

import com.mogul.demo.search.dto.WebtoonSearchResponse;
import com.mogul.demo.search.dto.WebtoonTotalSearchResponse;

public interface WebtoonSearchService {
	/*
	* todo
	* 제목 검색
	* 줄거리 검색
	* 장르 검색 - 단순일치
	* 통합 검색
	* */

	List<WebtoonSearchResponse> findByTitle(String keyword);
	List<WebtoonSearchResponse> findBySummary(String keyword);
	List<WebtoonSearchResponse> findByGenre(String keyword);
	WebtoonTotalSearchResponse totalSearch(String keyword);

}
