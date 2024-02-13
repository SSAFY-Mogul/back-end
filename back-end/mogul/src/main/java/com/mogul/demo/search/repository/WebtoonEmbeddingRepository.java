package com.mogul.demo.search.repository;

import com.mogul.demo.recommand.document.WebtoonEmbedding;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface WebtoonEmbeddingRepository extends ElasticsearchRepository<WebtoonEmbedding,String> {
    @Query("{\"knn\":{\"field\":\"webtoon_embedding\",\"query_vector\"::ev,\"k\":10,\"num_candidates\":100},\"_source\":[\"webtoon_id\"]}")
    List<WebtoonEmbedding> find(@Param("ev") String embeddingVector);
}
