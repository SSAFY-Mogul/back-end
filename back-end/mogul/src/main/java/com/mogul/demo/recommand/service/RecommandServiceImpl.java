package com.mogul.demo.recommand.service;

import com.mogul.demo.recommand.repository.EmbeddingRepository;
import com.mogul.demo.search.repository.WebtoonEmbeddingRepository;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommandServiceImpl implements RecommandService {
    private final static String elasticSearchHost = "http://i10a206.p.ssafy.io";
    private final static int elasticSearchPort = 49200;
    private final static String indexName = "webtoon-embeddings";
    private final static String fieldName = "webtoon_embeddings";

    private final EmbeddingRepository embeddingRepository;

    private final WebtoonEmbeddingRepository webtoonEmbeddingRepository;

    private final WebtoonService webtoonService;

    @Override
    @Transactional(readOnly = true)
    public List<WebtoonDetailResponse> ListRecommandWebtoons(Long webtoonId) {
        String embeddingVector = embeddingRepository.findByWebtoonId(webtoonId);
        List<WebtoonDetailResponse> data = webtoonEmbeddingRepository.find(embeddingVector).stream().map(webtoonEmbedding -> {
           return webtoonService.findWebtoonById(webtoonEmbedding.getWebtoonId());
        }).collect(Collectors.toList());
        return data;
    }
}
