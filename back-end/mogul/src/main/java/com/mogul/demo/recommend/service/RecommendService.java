package com.mogul.demo.recommend.service;

import com.mogul.demo.recommend.dto.WebtoonInfo;
import com.mogul.demo.recommend.dto.WebtoonRecommendResponse;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.client.Response;

public interface RecommendService {
    List<WebtoonRecommendResponse> ListRecommandWebtoons(Long webtoonId);
    List<WebtoonInfo> ElasticSearchKnnRequest(String queryVector) throws IOException;
    List<WebtoonInfo> parseResponse(String jsonResponse);
}
