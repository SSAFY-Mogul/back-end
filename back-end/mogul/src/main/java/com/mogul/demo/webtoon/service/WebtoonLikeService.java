package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;

public interface WebtoonLikeService {

    WebtoonLikeResponse getLike(Long webtoonId, Long userId);

    boolean addLike(Long webtoonId, Long userId);

    boolean removeLike(Long webtoonId, Long userId);
}