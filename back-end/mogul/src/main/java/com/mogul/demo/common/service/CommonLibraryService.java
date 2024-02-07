package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.dto.*;
import com.mogul.demo.util.CustomResponse;

import java.util.List;

public interface CommonLibraryService {
    LibraryDetailResponse getLibraryDetail(Long libraryId);

    Long addLibrary(LibraryCreateRequest libraryCreateRequest);

    CustomResponse addWebtoon(Long id, LibraryAddWebtoonRequest libraryAddWebtoonRequest);

    boolean addSubscription(SubcriptionRequest subcriptionRequest);

    boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest);

    boolean modifyLibrary(Long id, LibraryUpdateRequest libraryUpdateRequest);

    List findLibrariesByUserId();

    List findSubscription(int pageNumber, int pageSize);
}
