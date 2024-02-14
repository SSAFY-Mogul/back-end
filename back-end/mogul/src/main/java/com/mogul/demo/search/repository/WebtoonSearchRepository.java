package com.mogul.demo.search.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mogul.demo.search.document.WebtoonDocument;

public interface WebtoonSearchRepository extends ElasticsearchRepository<WebtoonDocument,String> {

	List<WebtoonDocument> findByTitleContainingOrSummaryContaining(String summary,String title);
	@Query("{\"match\": {\"webtoon_title\": {\"query\": \"?0\"}}}")
	List<WebtoonDocument> findByTitle(String keyword);
	@Query("{\"match\": {\"webtoon_summary\": {\"query\": \"?0\"}}}")
	List<WebtoonDocument> findBySummary(String keyword);
	@Query("{\"match\": {\"webtoon_genre\": {\"query\": \"?0\"}}}")
	List<WebtoonDocument> findByGenre(String keyword);


}
