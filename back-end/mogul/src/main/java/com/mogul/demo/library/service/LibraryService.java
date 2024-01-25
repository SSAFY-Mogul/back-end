package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryResponse;

import java.util.List;

public interface LibraryService {
    List<LibraryResponse> findLibrariesByWebtoonId(long webtoonId, int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesByUserId(long userId);

    LibraryResponse findLibraryById(long libraryId);
}
