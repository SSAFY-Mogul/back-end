package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;

public interface CommonLibraryService {
    LibraryDetailResponse getLibraryDetail(Long libraryId);
}
