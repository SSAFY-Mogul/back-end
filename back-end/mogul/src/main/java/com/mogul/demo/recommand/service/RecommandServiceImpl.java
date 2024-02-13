package com.mogul.demo.recommand.service;

import com.mogul.demo.common.dto.RecommandResponse;
import com.mogul.demo.recommand.repository.EmbeddingRepository;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommandServiceImpl implements RecommandService {
    private final static String elasticSearchHost = "http://i10a206.p.ssafy.io";
    private final static int elasticSearchPort = 49200;
    private final static String indexName = "webtoon-embeddings";
    private final static String fieldName = "webtoon_embeddings";

    private final EmbeddingRepository embeddingRepository;

    @Override
    public List<RecommandResponse> ListRecommandWebtoons(Long webtoonId) {
        String embeddingVector = embeddingRepository.findByWebtoonId(webtoonId);
    }
}
