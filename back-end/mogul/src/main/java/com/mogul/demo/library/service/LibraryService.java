package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.*;

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

    boolean addSubscription(SubcriptionRequest subcriptionRequest);

    boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest);

    boolean modifyLibrary(LibraryUpdateRequest libraryUpdateRequest);
}
