package com.mogul.demo.search.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mogul.demo.search.document.WebtoonDocument;

public interface WebtoonSearchRepository extends ElasticsearchRepository<WebtoonDocument,Long> {

	List<WebtoonDocument> findByTitleContainingOrSummaryContaining(String summary,String title);
	List<WebtoonDocument> findByTitleContaining(String keyword);
	List<WebtoonDocument> findBySummaryContaining(String keyword);
	List<WebtoonDocument> findByGenreContaining(String keyword);

}
