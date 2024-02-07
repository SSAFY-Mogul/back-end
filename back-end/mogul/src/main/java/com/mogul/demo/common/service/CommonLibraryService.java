package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.dto.LibraryCreateRequest;

public interface CommonLibraryService {
    LibraryDetailResponse getLibraryDetail(Long libraryId);

    Long addLibrary(LibraryCreateRequest libraryCreateRequest);
}
