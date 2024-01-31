package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.*;

import java.util.List;

public interface LibraryService {
    List<LibraryResponse> findLibrariesByWebtoonId(Long webtoonId, int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize);

    List<LibraryResponse> findLibrariesByUserId(Long userId);

    LibraryResponse findLibraryById(Long libraryId);

    Long addLibrary(LibraryCreateRequest libraryCreateRequest);

    boolean removeLibrary(Long id);

    boolean addWebtoon(LibraryAddWebtoonRequest libraryAddWebtoonRequest);

    List<SubscriptionResponse> findSubscription(Long userId, int pageNumber, int pageSize);

    boolean addSubscription(SubcriptionRequest subcriptionRequest);

    boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest);

    boolean modifyLibrary(LibraryUpdateRequest libraryUpdateRequest);
}
