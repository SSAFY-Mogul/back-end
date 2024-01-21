package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonAllPageRes;
import com.mogul.demo.webtoon.dto.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;

public interface WebtoonService {
    WebtoonMainPageRes findWebtoonMain(int page_number, int page_size);

    WebtoonAllPageRes findWebtoonAll(int pageNumber, int pageSize);

    WebtoonGenrePageRes findWebtoonByGenre(String genre, int pageNumber, int pageSize);
}
