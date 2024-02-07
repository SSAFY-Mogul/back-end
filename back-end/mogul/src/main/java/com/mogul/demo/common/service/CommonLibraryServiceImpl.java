package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonLibraryServiceImpl implements CommonLibraryService{

    private final LibraryService libraryService;

    private final WebtoonService webtoonService;

    @Override
    public LibraryDetailResponse getLibraryDetail(Long libraryId) {
        LibraryDetailResponse libraryDetailResponse = new LibraryDetailResponse();
        libraryDetailResponse.setLibrary(libraryService.findLibraryById(libraryId));
        libraryDetailResponse.setWebtoons(webtoonService.findWebtoonsByLibraryId(libraryId));
        return libraryDetailResponse;
    }
}
