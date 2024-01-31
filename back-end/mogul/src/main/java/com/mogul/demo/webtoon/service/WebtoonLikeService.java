package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;

public interface WebtoonLikeService {

    WebtoonLikeResponse getLike(long webtoonId, long userId);
}
