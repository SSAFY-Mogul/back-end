package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonResponse;

import java.util.List;

public interface WebtoonService {

    List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize);
}
