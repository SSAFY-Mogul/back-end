package com.mogul.demo.webtoon.service;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;

import java.util.List;

public interface WebtoonService {

    List<WebtoonResponse> findWebtoonOrderByGrade(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonOrderByLibraryCount(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonAll(int pageNumber, int pageSize);

    List<WebtoonResponse> findWebtoonAllByGenre(String genre, int pageNumber, int pageSize);

    WebtoonDetailResponse findWebtoonById(Long webtoonId);

    List<WebtoonResponse> findWebtoonsByLibraryId(Long libraryId);

    boolean isExist(Long webtoonId);

    void modifyWebtoonGrade(Long id, Float grade, Float drawingGrade, Float storyGrade, Float directingGrade);

    boolean addWebtoon(WebtoonAddRequest webtoonAddRequest);

    boolean removeWebtoon(Long id);
}
