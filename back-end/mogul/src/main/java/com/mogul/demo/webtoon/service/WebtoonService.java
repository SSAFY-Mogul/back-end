package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.response.WebtoonAllPageRes;
import com.mogul.demo.webtoon.response.WebtoonDetailPageRes;
import com.mogul.demo.webtoon.response.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.response.WebtoonMainPageRes;

public interface WebtoonService {
    WebtoonMainPageRes findWebtoonMain(int pageNumber, int pageSize);

    WebtoonAllPageRes findWebtoonAll(int pageNumber, int pageSize);

    WebtoonGenrePageRes findWebtoonByGenre(String genre, int pageNumber, int pageSize);

    WebtoonDetailPageRes findWebtoonDetail(long webtoonId, int pageNumber, int pageSize);
}
