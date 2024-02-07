package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.WebtoonDetailCommonResponse;
import com.mogul.demo.common.dto.WebtoonMainResponse;
import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;

public interface CommonWebtoonService {
    WebtoonMainResponse listWebtoonMain(int pageNumber, int pageSize);

    WebtoonDetailCommonResponse getWebtoonDetail(Long webtoonId, int pageNumber, int pageSize);

    WebtoonLikeResponse getLike(Long webtoonId);

    boolean addLike(Long webtoonId);

    boolean removeLike(Long webtoonId);
}
