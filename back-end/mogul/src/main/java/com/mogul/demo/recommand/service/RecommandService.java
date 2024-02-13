package com.mogul.demo.recommand.service;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;

import java.util.List;

public interface RecommandService {
    List<WebtoonDetailResponse> ListRecommandWebtoons(Long webtoonId);
}
