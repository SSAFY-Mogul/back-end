package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;

public interface WebtoonService {
    WebtoonMainPageRes findWebtoonMain(int page_number, int page_size);
}
