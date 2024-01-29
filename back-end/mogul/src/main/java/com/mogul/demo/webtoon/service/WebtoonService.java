package com.mogul.demo.webtoon.service;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;

import java.util.List;

public interface WebtoonService {

    List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonAllByGenre(String genre, int pageNumber, int pageSize);

    WebtoonDetailResponse findWebtoonById(long webtoonId);

    List<WebtoonResponse> findWebtoonsByLibraryId(long libraryId);

    boolean isExist(long webtoonId);

    void modifyWebtoonGrade(long id, float grade, float drawingGrade, float storyGrade, float directingGrade);
}
