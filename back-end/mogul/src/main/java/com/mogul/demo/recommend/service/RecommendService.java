package com.mogul.demo.recommend.service;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;

import java.util.List;

public interface RecommendService {
    List<WebtoonDetailResponse> ListRecommandWebtoons(Long webtoonId);
}
