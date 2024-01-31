package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;

public interface WebtoonLikeService {

    WebtoonLikeResponse getLike(long webtoonId, long userId);

    boolean addLike(long webtoonId, long userId);

    boolean removeLike(long webtoonId, long userId);
}