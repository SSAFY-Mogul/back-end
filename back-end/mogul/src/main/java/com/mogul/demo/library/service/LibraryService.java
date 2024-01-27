package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryAddWebtoonRequest;
import com.mogul.demo.library.dto.LibraryCreateRequest;
import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.dto.SubscriptionResponse;

import java.util.List;

public interface LibraryService {
    List<LibraryResponse> findLibrariesByWebtoonId(long webtoonId, int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesByUserId(long userId);

    LibraryResponse findLibraryById(long libraryId);

    Long addLibrary(LibraryCreateRequest libraryCreateRequest);

    boolean removeLibrary(long id);

    boolean addWebtoon(LibraryAddWebtoonRequest libraryAddWebtoonRequest);

    List<SubscriptionResponse> findSubscription(long userId, int pageNumber, int pageSize);
}
