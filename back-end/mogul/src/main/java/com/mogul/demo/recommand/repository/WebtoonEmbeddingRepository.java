package com.mogul.demo.recommand.repository;

import com.mogul.demo.recommand.document.WebtoonEmbedding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebtoonEmbeddingRepository extends ElasticsearchRepository<WebtoonEmbedding,Long> {
    @Query("{\"knn\":{\"field\":\"webtoon_embedding\",\"query_vector\":[],\"k\":10,\"num_candidates\":100},\"_source\":[\"webtoon_id\"]}")
    List<WebtoonEmbedding> findBySimilarity(String embeddingVector);
}
