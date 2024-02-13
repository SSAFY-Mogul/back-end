package com.mogul.demo.recommand.service;

import com.mogul.demo.common.dto.RecommandResponse;

import java.util.List;

public interface RecommandService {
    List<RecommandResponse> ListRecommandWebtoons(Long webtoonId);
}
