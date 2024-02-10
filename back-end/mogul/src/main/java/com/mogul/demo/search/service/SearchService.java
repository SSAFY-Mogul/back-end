package com.mogul.demo.search.service;

import java.util.List;

import com.mogul.demo.search.document.WebtoonDocument;
import com.mogul.demo.search.dto.WebtoonSearchResponse;

public interface SearchService {
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
	List<WebtoonSearchResponse> search(String keyword);
}
